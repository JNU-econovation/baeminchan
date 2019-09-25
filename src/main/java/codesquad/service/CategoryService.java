package codesquad.service;

import codesquad.domain.Category;
import codesquad.domain.CategoryRepository;
import codesquad.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;


    public Category create(CategoryDTO categoryDTO) {
        Category createdCategory = new Category()
                .title(categoryDTO.getTitle())
                .build();

        if (categoryDTO.hasParentId()) {
            Category parent = findById(categoryDTO.getParentId());
            createdCategory.parent(parent).build();

            log.info("parent in db: {}", parent);
            log.info("createdCategory: {}", createdCategory);

            categoryRepository.save(createdCategory);
            categoryRepository.save(parent);

            return createdCategory;
        }

        return categoryRepository.save(createdCategory);
    }

    public Category findByTitle(String title) {

        return categoryRepository.findByTitle(title).orElseThrow(RuntimeException::new);
    }

    public Category findById(Long id) {

        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Category update(Long id, CategoryDTO categoryDTO) {
        Category updatedCategory = findById(id);
        Category newParent = findById(categoryDTO.getParentId());

        if (categoryDTO.hasParentId()) {
            updatedCategory.update(categoryDTO.getTitle(), newParent);
            categoryRepository.save(updatedCategory);
            categoryRepository.save(newParent);

            return updatedCategory;
        }

        updatedCategory.update(categoryDTO.getTitle());

        return categoryRepository.save(updatedCategory);
    }

    public Category delete(Long id) {
        Category deletedCategory = findById(id);

        deletedCategory.delete();
        deletedCategory.getChildren().forEach(Category::delete);

        return categoryRepository.save(deletedCategory);
    }

    public List<Category> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        categoryList.stream()
                .filter(category -> !category.isDeleted()).collect(Collectors.toList());

        return categoryList;
    }
}
