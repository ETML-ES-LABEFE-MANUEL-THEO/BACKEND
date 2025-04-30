package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LotDTO {
    public record LotPaginatedThumbnail(LotThumbnail[] content, Integer page, Integer take, Integer total) {}
    public record LotThumbnail(Long id, String name, String location, BigDecimal initialPrice, BigDecimal lastPrice, String thumbnail) {}
    public record LotDetails(Long id,
                             String name,
                             String description,
                             String location,
                             BigDecimal initialPrice,
                             BigDecimal lastPrice,
                             List<String> medias,
                             LocalDateTime openDate,
                             LocalDateTime awardDate,
                             LocalDateTime closeDate,
                             @JsonIgnoreProperties("children") Category category,
                             @JsonIgnoreProperties("lot") List<Auction> auctions) {}
}
