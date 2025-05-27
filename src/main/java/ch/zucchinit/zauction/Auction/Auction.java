package ch.zucchinit.zauction.Auction;

import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Lot.Lot;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
public class Auction {
    @Id @GeneratedValue
    private Long id;
    private BigDecimal price;
    private LocalDateTime date;

    @ManyToOne @JoinColumn(name = "lot_id")
    private Lot lot;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    public Auction() {}
    public Auction(BigDecimal price, LocalDateTime date, Lot lot, User user) {
        this.price = price;
        this.date = date;
        this.lot = lot;
        this.user = user;
    }

    public boolean isLast() { return Objects.equals(lot.getLastAuction().id, this.id); }
}
