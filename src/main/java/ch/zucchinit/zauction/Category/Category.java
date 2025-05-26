package ch.zucchinit.zauction.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children;

    public Category() {}
    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public @JsonIgnore Category getParent() { return parent; }
}
