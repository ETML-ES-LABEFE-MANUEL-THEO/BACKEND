package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LotDTO {
    public record LotPaginatedThumbnail(LotThumbnail[] content, Integer page, Integer take, Long total) {}
    public record LotThumbnail(Long id, String name, String location, BigDecimal initialPrice, BigDecimal lastPrice, String thumbnail) {
        public LotThumbnail(Lot lot) {
            this(lot.getId(), lot.getName(), lot.getLocation(), lot.getInitialPrice(), lot.getLastPrice(), lot.getMedias().get(0));
        }
    }
    public record LotDetails(Long id, String name, String description, String location, BigDecimal initialPrice, BigDecimal lastPrice,
                             List<String> medias, LocalDateTime openDate, LocalDateTime awardDate, LocalDateTime closeDate,
                             LocalDateTime firstAuctionDate, LocalDateTime lastAuctionDate, Boolean lastBidder,
                             @JsonIgnoreProperties("children") List<Category> categories, List<AuctionDTO.AuctionPrice> auctions) {
        public LotDetails(Lot lot, boolean lastBidder, List<Category> categories, List<AuctionDTO.AuctionPrice> auctions) {
            this(lot.getId(), lot.getName(), lot.getDescription(), lot.getLocation(), lot.getInitialPrice(),
                    lot.getLastPrice(), lot.getMedias(), lot.getOpenDate(), lot.getAwardDate(), lot.getCloseDate(),
                    lot.getFirstAuction().getDate(), lot.getLastAuction().getDate(), lastBidder, categories, auctions);
        }
    }
}
