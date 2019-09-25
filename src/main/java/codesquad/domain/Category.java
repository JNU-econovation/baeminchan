package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @JsonIgnore
    private Category parent;

    @Where(clause = "deleted='false'")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", nullable = true)
    private List<Category> children = new ArrayList<>();

    @Column(name = "deleted", nullable = false)
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
        parent.addChild(this);

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

    public List<Category> getChildrenExceptDeleted() {
        return this.children.stream()
                .filter(child -> !child.isDeleted()).collect(Collectors.toList());
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void update(String title, Category newParent) {
        this.title = title;

        if (this.parent == null) {

            newParent.addChild(this);
            this.parent = newParent;
        }

        if (!this.parent.isSame(newParent)) {
            Category pastParent = this.parent;

            newParent.addChild(this);
            pastParent.getChildren().remove(this);

            this.parent = newParent;
        }
    }

    private boolean isSame(Category category) {

        return this.id.equals(category.getId());
    }

    public void update(String title) {
        this.title = title;
    }
}
