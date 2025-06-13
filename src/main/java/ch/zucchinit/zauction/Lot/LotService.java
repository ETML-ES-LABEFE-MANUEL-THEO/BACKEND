package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Auction.AuctionService;
import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Auth.UserService;
import ch.zucchinit.zauction.Category.Category;
import ch.zucchinit.zauction.Category.CategoryService;
import ch.zucchinit.zauction.Exceptions.GenericError;
import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import ch.zucchinit.zauction.Utils.FieldUpdater;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static ch.zucchinit.zauction.Lot.LotSpecifications.*;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final LotMediaService lotMediaService;
    private final CategoryService categoryService;
    private final AuctionService auctionService;
    private final UserService userService;

    public LotService(LotRepository lotRepository, CategoryService categoryRepository, LotMediaService lotMediaService,
                      AuctionService auctionService, UserService userService) {
        this.lotRepository = lotRepository;
        this.lotMediaService = lotMediaService;
        this.categoryService = categoryRepository;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    public Lot findById(Long id) { return this.lotRepository.findById(id).orElseThrow(ResourceNotFound::new); }

    public Lot getRestrictedLot(Long id) {
        Lot lot = findById(id);
        if (lot.getTransferDate() != null || lot.getCloseDate() != null) {
            List<User> usersToRestrict = Stream.of(lot.getSellerUser(), lot.getBuyerUser().orElse(null)).filter(Objects::nonNull).toList();
            userService.restrictUsers(usersToRestrict);
        }
        else if (lot.getPublishDate() == null) userService.restrictUser(lot.getSellerUser());

        return lot;
    }

    public LotDTO.PaginatedLot<LotDTO.LotThumbnail> findByPageWithCategoryAndSearch(Integer page, Integer take, LotDTO.LotFilter filters) {
        int safePage = (page != null && page >= 0) ? page : 0;
        int safeTake = (take != null && take > 0) ? take : 10;
        Map<String, Object> extraData = new HashMap<>();

        Specification<Lot> spec = Specification.where(isAvailable());
        if (filters != null) {
            if (filters.categoryId() != null) spec = spec.and(hasCategoryId(categoryService, filters.categoryId()));
            if (filters.search() != null) spec = spec.and(multiFieldSearch(filters.search()));
        }

        lotRepository.findAll(spec, PageRequest.of(0, 1, Sort.by("lastPrice").ascending()))
                .stream().findFirst().ifPresent(min -> extraData.put("min", min.getLastPrice()));

        lotRepository.findAll(spec, PageRequest.of(0, 1, Sort.by("lastPrice").descending()))
                .stream().findFirst().ifPresent(max -> extraData.put("max", max.getLastPrice()));

        if (filters != null) spec = spec.and(betweenMaxAuctionPrice(filters.minPrice(), filters.maxPrice()));

        Pageable pageable = PageRequest.of(safePage, safeTake);
        Page<Lot> lots = lotRepository.findAll(spec, pageable);
        List<LotDTO.LotThumbnail> thumbnails = lots.getContent().stream().map(LotDTO.LotThumbnail::new).toList();

        return new LotDTO.PaginatedLot<>(thumbnails, safePage, safeTake, lots.getTotalElements(), extraData.isEmpty() ? null : extraData);
    }

    public LotDTO.PaginatedLot<LotDTO.LotHistory> findByPageForHistory(Integer page, Integer take, Boolean isSell) {
        User user = userService.getUserFromContext();
        Specification<Lot> spec = Specification.where(isSell ? isSeller(user.getId()) : isBuyer(user.getId())).and(orderByDatesDesc());

        Pageable pageable = PageRequest.of(page, take);
        Page<Lot> lots = this.lotRepository.findAll(spec, pageable);

        List<LotDTO.LotHistory> histories = lots.getContent().stream().map(lot -> new LotDTO.LotHistory(lot, lot.isLastBidder(user))).toList();
        return new LotDTO.PaginatedLot<>(histories, page, take, lots.getTotalElements());
    }

    public LotDTO.LotDetails getLotDetails(Lot lot) {
        List<Category> categories = categoryService.getReverseCategories(lot.getCategory().getId());
        List<AuctionDTO.AuctionPrice> auctions = auctionService.findAuctionsByPeriod(lot, AuctionDTO.TimePeriod.WEEK);

        User user = userService.getUserFromContext();
        return new LotDTO.LotDetails(lot, lot.isLastBidder(user), lot.getSellerUser().isSame(user), categories, auctions);
    }

    @Transactional
    public Lot createLot(LotDTO.LotCreation lotCreation, Map<Integer, MultipartFile> medias) throws IOException {
        Category category = categoryService.findById(lotCreation.categoryId());
        User user = userService.getUserFromContext();

        Lot lot = lotRepository.save(new Lot(lotCreation.name(), lotCreation.description(), lotCreation.location(),
                lotCreation.initialPrice(), new ArrayList<>(), category, user));

        return lotMediaService.insertLotMedias(lot, medias);
    }

    @Transactional
    public Lot updateLot(Long id, LotDTO.LotModification lotModification,
                         List<LotDTO.LotMediaAction> metas,
                         List<MultipartFile> medias) throws IOException
    {
        Lot lot = findById(id);
        userService.restrictUser(lot.getSellerUser());

        if (lotModification.categoryId() != null && !Objects.equals(lot.getCategory().getId(), lotModification.categoryId())) {
            Category category = categoryService.findById(lotModification.categoryId());
            lot.setCategory(category);
        }

        FieldUpdater.updateIfChanged(lot::getName, lot::setName, lotModification.name());
        FieldUpdater.updateIfChanged(lot::getDescription, lot::setDescription, lotModification.description());
        FieldUpdater.updateIfChanged(lot::getLocation, lot::setLocation, lotModification.location());
        FieldUpdater.updateIfChanged(lot::getInitialPrice, lot::setInitialPrice, lotModification.initialPrice());
        FieldUpdater.updateIfChanged(lot::getPublishDate, lot::setPublishDate, lotModification.publishDate());
        FieldUpdater.updateIfChanged(lot::getCloseDate, lot::setCloseDate, lotModification.closeDate());

        if (metas != null) return lotMediaService.updateLotMedias(lot, metas, medias);
        return lotRepository.save(lot);
    }

    public void publishLot(Lot lot) {
        userService.restrictUser(lot.getSellerUser());
        if (lot.getPublishDate() != null) throw new GenericError(HttpStatus.CONFLICT, "Le lot est déjà publié");

        lot.setPublishDate(LocalDateTime.now());
        lotRepository.save(lot);
    }

    public void closeLot(Lot lot) {
        userService.restrictUser(lot.getSellerUser());

        if (lot.getPublishDate() == null) throw new GenericError(HttpStatus.CONFLICT, "Le lot n'est pas publié");
        if (lot.getCloseDate() != null) throw new GenericError(HttpStatus.CONFLICT, "Le lot est déjà clôturé");

        Optional<Auction> lastAuction = lot.getLastAuction();
        lot.setCloseDate(LocalDateTime.now());

        lastAuction.ifPresent(auction -> lot.setBuyerUser(auction.getUser()));
        lotRepository.save(lot);
    }

    @Transactional
    public LotDTO.LotTransferResult transferLot(Lot lot) {
        User buyerUser = lot.getBuyerUser().orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        userService.restrictUser(buyerUser);

        if (lot.getPublishDate() == null) throw new GenericError(HttpStatus.CONFLICT, "Le lot n'est pas publié");
        if (lot.getCloseDate() == null) throw new GenericError(HttpStatus.CONFLICT, "Le lot n'est pas clôturé");
        if (lot.getTransferDate() != null) throw new GenericError(HttpStatus.CONFLICT, "Le lot est déjà transféré");

        BigDecimal newBuyerBalance = buyerUser.getBalance().subtract(lot.getLastPrice());
        userService.setBalanceForUser(buyerUser, newBuyerBalance);

        User sellerUser = lot.getSellerUser();
        BigDecimal newSellerBalance = sellerUser.getBalance().add(lot.getLastPrice());
        userService.setBalanceForUser(sellerUser, newSellerBalance);

        lot.setTransferDate(LocalDateTime.now());
        lotRepository.save(lot);

        return new LotDTO.LotTransferResult(newBuyerBalance);
    }

    @Scheduled(cron = "0 0 * * * *")
    protected void setBuyerUserOnCloseLot() {
        List<Lot> lots = lotRepository.findByCloseDateAfterAndBuyerUserNull(LocalDateTime.now());
        lots.forEach(lot -> lot.getLastAuction().ifPresent(a -> lot.setBuyerUser(a.getUser())));
        lotRepository.saveAll(lots);
    }
}