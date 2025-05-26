package ch.zucchinit.zauction.Auction;

import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Exceptions.ExceptionsDTO;
import ch.zucchinit.zauction.Exceptions.ValidationError;
import ch.zucchinit.zauction.Lot.Lot;
import ch.zucchinit.zauction.Utils.AuthContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public AuctionDTO.AuctionPrice createAuction(Lot lot, AuctionDTO.AuctionRequest auctionRequest) {
        if (lot.getLastPrice().compareTo(auctionRequest.price()) >= 0) {
            ExceptionsDTO.ValidationError error = new ExceptionsDTO.ValidationError("price", "Une enchère supérieur existe", Map.of("highestPrice", lot.getLastPrice()));
            throw new ValidationError(List.of(error));
        }

        User user = AuthContext.getUserFromContext();
        BigDecimal priceDiff = lot.getAuctions().stream()
                .filter(a -> Objects.equals(a.getUser().getId(), user.getId()))
                .max(Comparator.comparing(Auction::getDate))
                .map(a -> auctionRequest.price().subtract(a.getPrice()))
                .orElse(auctionRequest.price());

        if (!user.hasSufficientBalance(priceDiff)) {
            ExceptionsDTO.ValidationError error = new ExceptionsDTO.ValidationError("balance", "Le solde de l'utilisateur est insuffisant");
            throw new ValidationError(List.of(error));
        }

        Auction auction = this.auctionRepository.save(new Auction(auctionRequest.price(), LocalDateTime.now(), lot, user));
        return getAuctionPrice(auction);
    }

    public List<AuctionDTO.AuctionPrice> findAuctionsByPeriod(Lot lot, AuctionDTO.TimePeriod timePeriod){
        final int MAX_RETURNED_AUCTIONS = 50;

        LocalDateTime startDate = getPeriodKey(timePeriod);
        List<Auction> auctions = auctionRepository.findByLotAndDateAfterOrderByDateDesc(lot, startDate);

        int total = auctions.size();

        if (total <= MAX_RETURNED_AUCTIONS) {
            return auctions.stream()
                    .map(e -> new AuctionDTO.AuctionPrice(e.getDate(), e.getPrice()))
                    .collect(Collectors.toList());
        }
        else {
            double step = (double) total / MAX_RETURNED_AUCTIONS;
            List<AuctionDTO.AuctionPrice> result = new ArrayList<>();

            for (int i = 0; i < MAX_RETURNED_AUCTIONS; i++) {
                int index = (int) Math.floor(i * step);
                if (index >= total) index = total - 1;

                Auction auction = auctions.get(index);
                result.add(getAuctionPrice(auction));
            }

            return result;
        }
    }

    public AuctionDTO.AuctionPrice getAuctionPrice(Auction auction) {
        return new AuctionDTO.AuctionPrice(auction.getDate(), auction.getPrice());
    }

    private LocalDateTime getPeriodKey(AuctionDTO.TimePeriod timePeriod) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = switch (timePeriod) {
            case WEEK -> now.minusDays(7);
            case MONTH -> now.withDayOfMonth(1);
            case QUARTER -> {
                int startMonth = ((now.getMonthValue() - 1) / 3) * 3 + 1;
                yield LocalDateTime.of(now.getYear(), startMonth, 1, 0, 0);
            }
            case SEMESTER -> {
                int startSemester = (now.getMonthValue() <= 6) ? 1 : 7;
                yield LocalDateTime.of(now.getYear(), startSemester, 1, 0, 0);
            }
            case YEAR -> now.withDayOfYear(1);
            case ALL -> LocalDateTime.of(0, 1, 1, 0, 0);
        };

        return date.toLocalDate().atStartOfDay();
    }
}
