package codesquad.service;

import codesquad.domain.Category;
import codesquad.domain.CategoryRepository;
import codesquad.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    private static final String CHILD_TITLE = "곧 죽어도 피자";
    private static final String PARENT_TITLE = "부모 메뉴도 피자";

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category parent;
    private Category child;

    @Before
    public void setUp() throws Exception {
        parent = new Category()
                .title(PARENT_TITLE)
                .build();

        child = new Category()
                .title(CHILD_TITLE)
                .build();


       when(categoryRepository.save(parent)).thenReturn(parent);
       when(categoryRepository.findByTitle(PARENT_TITLE)).thenReturn(Optional.of(parent));

        when(categoryRepository.save(child)).thenReturn(child);
        when(categoryRepository.findById(child.getId())).thenReturn(Optional.of(child));
        when(categoryRepository.findByTitle(CHILD_TITLE)).thenReturn(Optional.of(child));
    }

    @Test
    public void create_no_parent() {
        when(categoryRepository.save(child)).thenReturn(child);

        assertThat(categoryService.create(new CategoryDTO(child.getTitle(), null))).isEqualTo(child);
    }

    @Test
    public void create_contain_parent() {
        Category category = new Category()
                .title(CHILD_TITLE)
                .parent(parent)
                .build();

        when(categoryRepository.save(category)).thenReturn(category);

        Category dbCategory = categoryService.create(new CategoryDTO(child.getTitle(), null));

        assertThat(dbCategory).isEqualTo(category);
        assertThat(dbCategory.getParent()).isEqualTo(parent);
        assertThat(categoryRepository.findByTitle(PARENT_TITLE)
                .orElseThrow(RuntimeException::new).getChildren().get(0).getId())
                .isEqualTo(category.getId());
    }

    @Test
    public void findByTitle() {
        assertThat(categoryService.findByTitle(CHILD_TITLE).getTitle()).isEqualTo(child.getTitle());
    }

    @Test
    public void findById() {
        assertThat(categoryService.findById(child.getId()).getTitle()).isEqualTo(child.getTitle());
    }

    @Test
    public void update() {
        CategoryDTO categoryDTO = new CategoryDTO(child.getTitle(), child.getParent().getId());

        assertThat(categoryService.update(child.getId(), categoryDTO).getId()).isEqualTo(child.getId());
    }

    @Test
    public void delete() {
        assertThat(categoryService.delete(child.getId()).isDeleted()).isTrue();
    }

    @Test
    public void show_children_except_deleted() {

    }
}
