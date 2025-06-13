package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auction.AuctionDTO;
import ch.zucchinit.zauction.Category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class LotDTO {
    public record PaginatedLot<T>(List<T> content, Integer page, Integer take, Long total, Map<String, Object> details) {
        public PaginatedLot(List<T> content, Integer page, Integer take, Long total) {
            this(content, page, take, total, null);
        }
    }

    public record LotFilter(Long categoryId, String search, BigDecimal minPrice, BigDecimal maxPrice) {}

    public record LotThumbnail(Long id, String name, String location, BigDecimal initialPrice, BigDecimal lastPrice, String thumbnail) {
        public LotThumbnail(Lot lot) {
            this(lot.getId(), lot.getName(), lot.getLocation(), lot.getInitialPrice(), lot.getLastPrice(), lot.getMedias().get(0));
        }
    }

    public record LotDetails(Long id, String name, String description, String location, BigDecimal initialPrice, BigDecimal lastPrice,
                             List<String> medias, LocalDateTime publishDate, LocalDateTime closeDate, LocalDateTime transferDate,
                             LocalDateTime firstAuctionDate, LocalDateTime lastAuctionDate, Boolean isLastBidder, Boolean isSeller,
                             @JsonIgnoreProperties("children") List<Category> categories, List<AuctionDTO.AuctionPrice> auctions) {

        public LotDetails(Lot lot, boolean isLastBidder, boolean isSeller, List<Category> categories, List<AuctionDTO.AuctionPrice> auctions) {
            this(lot.getId(), lot.getName(), lot.getDescription(), lot.getLocation(), lot.getInitialPrice(),
                    lot.getLastPrice(), lot.getMedias(), lot.getPublishDate(), lot.getCloseDate(), lot.getTransferDate(),
                    lot.getFirstAuction().map(Auction::getDate).orElse(null),
                    lot.getLastAuction().map(Auction::getDate).orElse(null),
                    isLastBidder, isSeller, categories != null ? categories : List.of(),
                    auctions != null ? auctions : List.of()
            );
        }
    }

    public record LotHistory(Long id, String name, String shortDesc, String location, BigDecimal initialPrice, BigDecimal lastPrice,
                             String thumbnail, LocalDateTime publishDate, LocalDateTime closeDate, LocalDateTime transferDate, boolean isLastBidder) {
        public LotHistory(Lot lot, boolean isLastBidder) {
            this(lot.getId(), lot.getName(), lot.getDescription().length() < 50 ? lot.getDescription() : lot.getDescription().substring(50),
                lot.getLocation(), lot.getInitialPrice(), lot.getLastPrice(), lot.getMedias().get(0), lot.getPublishDate(), lot.getCloseDate(),
                lot.getTransferDate(), isLastBidder);
        }
    }

    public record LotCreation(
        @NotNull(message = "Le nom est obligatoire")
        @Size(min = 10, max = 50)
        String name,

        @NotNull(message = "La description est obligatoire")
        @Size(min = 10, max = 250)
        String description,

        @NotNull(message = "L'emplacement est obligatoire")
        @Size(min = 5, max = 50)
        String location,

        @NotNull(message = "Le prix initial est obligatoire")
        @DecimalMin(value = "1", message = "Le prix doit être supérieur à 0")
        @Digits(integer = 10, fraction = 0, message = "Le prix doit être un entier")
        BigDecimal initialPrice,

        @Future(message = "La date doit être dans le futur")
        LocalDateTime publishDate,

        @Future(message = "La date doit être dans le futur")
        LocalDateTime closeDate,

        @NotNull(message = "La catégorie est obligatoire")
        Long categoryId
    ) { }

    public record LotMediaAction(String action, Integer from, Integer to) { }
    public record LotModification(
            @Size(min = 10, max = 50)
            String name,

            @Size(min = 10, max = 250)
            String description,

            @Size(min = 5, max = 50)
            String location,

            @DecimalMin(value = "1", message = "Le prix doit être supérieur à 0")
            @Digits(integer = 10, fraction = 0, message = "Le prix doit être un entier")
            BigDecimal initialPrice,

            @Future(message = "La date doit être dans le futur")
            LocalDateTime publishDate,

            @Future(message = "La date doit être dans le futur")
            LocalDateTime closeDate,

            Long categoryId
    ) {}

    public record LotTransferResult(BigDecimal newBalance) {}
}
