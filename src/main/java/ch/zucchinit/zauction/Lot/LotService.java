package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Auction.AuctionService;
import ch.zucchinit.zauction.Auth.UserService;
import ch.zucchinit.zauction.Category.Category;
import ch.zucchinit.zauction.Category.CategoryService;
import ch.zucchinit.zauction.Exceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static ch.zucchinit.zauction.Lot.LotSpecifications.*;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final CategoryService categoryService;
    private final AuctionService auctionService;
    private final UserService userService;

    public LotService(LotRepository lotRepository, CategoryService categoryRepository, AuctionService auctionService, UserService userService) {
        this.lotRepository = lotRepository;
        this.categoryService = categoryRepository;
        this.auctionService = auctionService;
        this.userService = userService;
    }

    public Lot findById(Long id) { return this.lotRepository.findById(id).orElseThrow(ResourceNotFound::new); }

    public LotDTO.LotPaginatedThumbnail findByPageWithCategoryAndSearch(Integer page, Integer take, Long categoryId, String search) {
        Specification<Lot> spec = Specification.where(hasCategoryId(categoryService, categoryId)).and(multiFieldSearch(search)).and(isFinished());
        Pageable pageable = PageRequest.of(page, take);

        Page<Lot> lots = this.lotRepository.findAll(spec, pageable);
        LotDTO.LotThumbnail[] thumbnails = lots.getContent().stream().map(LotDTO.LotThumbnail::new).toArray(LotDTO.LotThumbnail[]::new);

        return new LotDTO.LotPaginatedThumbnail(thumbnails, page, take, lots.getTotalElements());
    }

    public LotDTO.LotDetails getLotDetails(Lot lot) {
        List<Category> categories = categoryService.getReverseCategories(lot.getCategory().getId());
        List<AuctionDTO.AuctionPrice> auctions = auctionService.findAuctionsByPeriod(lot, AuctionDTO.TimePeriod.WEEK);

        Long userId = userService.getUserFromContext().getId();
        boolean lastBidder = lot.getLastAuction().getUser().getId().equals(userId);

        return new LotDTO.LotDetails(lot, lastBidder, categories, auctions);
    }
}