package ch.zucchinit.zauction.Auction;

import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Lot.Lot;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
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

    public Long getId() { return id; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getDate() { return date; }
    public Lot getLot() { return lot; }
    public User getUser() { return user; }
}
