package codesquad.service;

import codesquad.domain.Category;
import codesquad.domain.CategoryRepository;
import codesquad.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category create(Category category) {

        if (category.hasParent()) {
            categoryRepository.save(category.getParent());
        }

        return categoryRepository.save(category);
    }

    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title).orElseThrow(RuntimeException::new);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Category update(CategoryDTO categoryDTO) {
        Category updatedCategory = findByTitle(categoryDTO.getTitle());
        updatedCategory.update(categoryDTO);

        return categoryRepository.save(updatedCategory);
    }

    public Category delete(Category child) {
        Category deletedCategory = findById(child.getId());
        deletedCategory.delete();

        return categoryRepository.save(deletedCategory);
    }
}
