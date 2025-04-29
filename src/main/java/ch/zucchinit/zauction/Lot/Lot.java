package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Category.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Lot {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String location;
    private BigDecimal initialPrice;
    private LocalDate openDate;
    private LocalDate awardDate;
    private LocalDate closeDate;

    @ManyToOne @JoinColumn(name = "category_id")
    private Category category;

    public Lot() {}
    public Lot(String name, String description, String location, BigDecimal initialPrice, Category category) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.initialPrice = initialPrice;
        this.category = category;
        this.openDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public BigDecimal getInitialPrice() { return initialPrice; }
    public LocalDate getOpenDate() { return openDate; }
    public LocalDate getAwardDate() { return awardDate; }
    public LocalDate getCloseDate() { return closeDate; }
    public Category getCategory() { return category; }
}
