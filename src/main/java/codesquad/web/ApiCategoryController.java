package codesquad.web;

import codesquad.domain.Category;
import codesquad.dto.CategoryDTO;
import codesquad.response.ResponseGenerator;
import codesquad.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category")
    public ResponseEntity<List<Category>> showCategoryList() {
        List<Category> categoryList = categoryService.findAll();

        return ResponseGenerator.generateResponseEntity(categoryList, HttpStatus.FOUND);
    }

    @PostMapping("/admin/category/create")
    public ResponseEntity<Category> createCategory(CategoryDTO categoryDTO) {
        categoryService.create(new Category(categoryDTO));

        return ResponseGenerator.generateResponseEntity(HttpStatus.FOUND);
    }

    @PutMapping("/admin/category/{id}/update")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);

        return ResponseGenerator.generateResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{id}/delete")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseGenerator.generateResponseEntity(HttpStatus.OK);
    }
}
