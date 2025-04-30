package ch.zucchinit.zauction.Auction;

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

    public Auction() {}
    public Auction(BigDecimal price, Lot lot) {
        this.price = price;
        this.date = LocalDateTime.now();
        this.lot = lot;
    }

    public Long getId() { return id; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getDate() { return date; }
    public Lot getLot() { return lot; }
}
