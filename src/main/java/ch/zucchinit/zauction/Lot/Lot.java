package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Category.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Entity
@Data
public class Lot {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String location;
    private BigDecimal initialPrice;
    private LocalDateTime openDate;
    private LocalDateTime awardDate;
    private LocalDateTime closeDate;

    @ElementCollection
    private List<String> medias;

    @ManyToOne @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Auction> auctions;

    public Lot() {}
    public Lot(String name, String description, String location, BigDecimal initialPrice, List<String> medias, Category category) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.initialPrice = initialPrice;
        this.medias = medias;
        this.category = category;
        this.openDate = LocalDateTime.now();
    }

    public Auction getFirstAuction() { return auctions.stream().min(Comparator.comparing(Auction::getDate)).orElse(null); }
    public Auction getLastAuction() { return auctions.stream().max(Comparator.comparing(Auction::getDate)).orElse(null); }
    public BigDecimal getLastPrice() {
        Auction lastAuction = getLastAuction();
        return lastAuction != null ? lastAuction.getPrice() : initialPrice;
    }
}
