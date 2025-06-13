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
    private @Column(nullable = false) BigDecimal price;
    private @Column(nullable = false) LocalDateTime date;

    @ManyToOne @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Auction() {}
    public Auction(BigDecimal price, LocalDateTime date, Lot lot, User user) {
        this.price = price;
        this.date = date;
        this.lot = lot;
        this.user = user;
    }

    public boolean isLast() { return Objects.equals(lot.getLastAuction().map(Auction::getId).orElse(null), this.id); }
}
