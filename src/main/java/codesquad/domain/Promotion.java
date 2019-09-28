package codesquad.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "promotion_type")
    @Enumerated(EnumType.STRING)
    private PromotionType type;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "description")
    private String describe;

    @Column(nullable = false, name = "original_price")
    private int originalPrice;

    @Column(nullable = false, name = "final_price")
    private int finalPrice;

    public boolean isType(PromotionType promotionType) {
        return this.type == promotionType;
    }
}
