package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Category.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
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

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public BigDecimal getInitialPrice() { return initialPrice; }
    public BigDecimal getLastPrice() { return auctions.stream().map(Auction::getPrice).max(BigDecimal::compareTo).orElse(initialPrice); }
    public LocalDateTime getOpenDate() { return openDate; }
    public LocalDateTime getAwardDate() { return awardDate; }
    public LocalDateTime getCloseDate() { return closeDate; }
    public List<String> getMedias() { return medias; }
    public Category getCategory() { return category; }
    public List<Auction> getAuctions() {
        return auctions.stream().sorted((a, b) -> Math.toIntExact(b.getId() - a.getId())
        ).collect(Collectors.toList());
    }
}
