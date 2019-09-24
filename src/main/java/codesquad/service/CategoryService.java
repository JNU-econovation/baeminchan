package codesquad.service;

import codesquad.domain.Category;
import codesquad.domain.CategoryRepository;
import codesquad.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Category update(Long id, CategoryDTO categoryDTO) {
        Category updatedCategory = findById(id);
        updatedCategory.update(categoryDTO);

        return categoryRepository.save(updatedCategory);
    }

    public Category delete(Long id) {
        Category deletedCategory = findById(id);
        deletedCategory.delete();

        return categoryRepository.save(deletedCategory);
    }

    public List<Category> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        categoryList.stream()
                .filter(category -> !category.isDeleted()).collect(Collectors.toList());

        return categoryList;
    }
}
