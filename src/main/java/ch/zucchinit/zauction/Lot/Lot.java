package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Auction.Auction;
import ch.zucchinit.zauction.Auth.User;
import ch.zucchinit.zauction.Category.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Lot {

    @Id @GeneratedValue
    private Long id;
    private @Column(nullable = false) String name;
    private @Column(nullable = false) String description;
    private @Column(nullable = false) String location;
    private @Column(nullable = false) BigDecimal initialPrice;
    private @Column(nullable = false) BigDecimal lastPrice;
    private LocalDateTime publishDate;
    private LocalDateTime closeDate;
    private LocalDateTime transferDate;
    private LocalDateTime creationDate;

    @ElementCollection
    private List<String> medias;

    @ManyToOne @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL)
    private List<Auction> auctions  = new ArrayList<>();

    @ManyToOne @JoinColumn(name = "seller_user_id", nullable = false)
    private User sellerUser;

    @ManyToOne @JoinColumn(name = "buyer_user_id")
    private User buyerUser;

    public Lot() {}
    public Lot(String name, String description, String location, BigDecimal initialPrice, List<String> medias, Category category, User sellerUser) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.initialPrice = initialPrice;
        this.lastPrice = initialPrice;
        this.medias = medias;
        this.category = category;
        this.creationDate = LocalDateTime.now();
        this.sellerUser = sellerUser;
        this.auctions = new ArrayList<>();
    }

    public Optional<User> getBuyerUser() {
        if (buyerUser != null) return Optional.of(buyerUser);
        if (closeDate != null && LocalDateTime.now().isAfter(closeDate)) return getLastAuction().map(Auction::getUser);
        return Optional.empty();
    }

    public Optional<Auction> getFirstAuction() { return auctions.stream().min(Comparator.comparing(Auction::getDate)); }
    public Optional<Auction> getLastAuction() { return auctions.stream().max(Comparator.comparing(Auction::getDate)); }
    public boolean isLastBidder(User user) { return getLastAuction().map(a -> a.getUser().isSame(user)).orElse(false); }
}
