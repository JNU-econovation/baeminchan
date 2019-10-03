package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryTest {
    private Category yangSik;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        yangSik = new Category()
                .title("양식")
                .build();

        categoryRepository.save(yangSik);
    }

    @Test
    @Transactional
    public void 관계매핑_잘되었는지() {
        Category pizza = new Category()
                .title("피자")
                .build();

        yangSik.addChild(pizza);
        pizza.setParent(yangSik);

        categoryRepository.save(pizza);
        categoryRepository.save(yangSik);

        Category dbYanSik = categoryRepository.findById(yangSik.getId()).orElseThrow(RuntimeException::new);

        assertThat(dbYanSik.getChildren().get(0).getId()).isEqualTo(pizza.getId());
    }
}
