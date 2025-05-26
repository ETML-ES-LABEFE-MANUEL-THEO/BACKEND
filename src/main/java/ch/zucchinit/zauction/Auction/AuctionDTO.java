package ch.zucchinit.zauction.Auction;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionDTO {
    public enum TimePeriod {
        ALL, YEAR, SEMESTER, QUARTER, MONTH, WEEK
    }

    public record AuctionPrice(LocalDateTime date, BigDecimal price) {}
    public record AuctionRequest(
            @NotNull(message = "Le prix est obligatoire")
            @DecimalMin(value = "1", inclusive = true, message = "Le prix doit être supérieur à 0")
            @Digits(integer = 10, fraction = 0, message = "Le prix doit être un entier")
            BigDecimal price
    ){}
}
