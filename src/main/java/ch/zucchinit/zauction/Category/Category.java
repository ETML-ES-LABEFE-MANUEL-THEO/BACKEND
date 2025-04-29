package ch.zucchinit.zauction.Category;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Category> getChildren() { return children; }
}
