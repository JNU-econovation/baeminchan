package codesquad.service;

import codesquad.domain.Promotion;
import codesquad.domain.PromotionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.PromotionType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PromotionServiceTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionService promotionService;

    private Promotion promotion1;
    private Promotion promotion2;
    private Promotion promotion3;
    private List<Promotion> promotionList;


    @Before
    public void setUp() throws Exception {
        promotion1 = new Promotion(1l, SUB_DISH, "pizza1", "곧 죽어도 피자", 10000, 5000);
        promotion2 = new Promotion(2l, MAIN_DISH, "pizza2", "곧 죽어도 피자", 10000, 5000);
        promotion3 = new Promotion(3l, SOUP_AND_STEW, "pizza3", "곧 죽어도 피자", 10000, 5000);

        promotionList = new ArrayList();
        promotionList.add(promotion1);
        promotionList.add(promotion2);
        promotionList.add(promotion3);

    }

    @Test
    public void showAll() {
        when(promotionRepository.findAll()).thenReturn(promotionList);

        assertThat(promotionService.findAll().size()).isEqualTo(3);
        assertThat(promotionService.findAll().get(0).getTitle()).isEqualTo("pizza1");
    }

    @Test
    public void show_with_same_type() {
        when(promotionRepository.findAll()).thenReturn(promotionList);

        assertThat(promotionService.findSubDishList().size()).isEqualTo(1);
        assertThat(promotionService.findSubDishList().get(0).getType()).isEqualTo(SUB_DISH);

        assertThat(promotionService.findMainDishList().size()).isEqualTo(1);
        assertThat(promotionService.findMainDishList().get(0).getType()).isEqualTo(MAIN_DISH);

        assertThat(promotionService.findSoupAndStewList().size()).isEqualTo(1);
        assertThat(promotionService.findSoupAndStewList().get(0).getType()).isEqualTo(SOUP_AND_STEW);
    }
}
