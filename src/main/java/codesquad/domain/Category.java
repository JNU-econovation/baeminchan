package codesquad.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Where(clause = "deleted='false'")
    @JoinColumn(name = "parentId", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Category parent;

    @Where(clause = "deleted='false'")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", nullable = true)
    private List<Category> children = new ArrayList<>();

    @Column(name = "deleted")
    private boolean deleted = false;

    public Category(Category category) {
        this.title = category.title;
        this.parent = category.parent;
        this.children = category.children;
        this.deleted = category.deleted;
    }

    public Category title(String title) {
        this.title = title;
        return this;
    }

    public Category parent(Category parent) {
        this.parent = parent;
        return this;
    }

    public Category children(List children) {
        this.children = children;
        return this;
    }

    public Category build() {
        return new Category(this);
    }

    public void addChild(Category child) {
        children.add(child);
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void delete() {
        this.deleted = true;
    }
}
