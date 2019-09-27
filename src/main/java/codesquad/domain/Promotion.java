package codesquad.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "type")
    private PromotionType type;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "desc")
    private String describe;

    @Column(nullable = false, name = "original_price")
    private int originalPrice;

    @Column(nullable = false, name = "final_price")
    private int finalPrice;

    public boolean isType(PromotionType promotionType) {
        return this.type == promotionType;
    }
}
