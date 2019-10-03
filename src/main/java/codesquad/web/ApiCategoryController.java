package codesquad.web;

import codesquad.domain.Category;
import codesquad.dto.CategoryDTO;
import codesquad.response.ResponseGenerator;
import codesquad.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiCategoryController {
    private static final Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    private final CategoryService categoryService;

    @GetMapping("/admin/category/search")
    public ResponseEntity<List<Category>> showCategoryList() {
        List<Category> categoryList = categoryService.findAll();

        log.info("list: {}", categoryList);

        return ResponseGenerator.generateResponseEntity(categoryList, HttpStatus.FOUND);
    }

    @GetMapping("/admin/category/{id}")
    public ResponseEntity<Category> categoryDetailPage(@PathVariable Long id) {

        return ResponseGenerator.generateResponseEntity(categoryService.findById(id), HttpStatus.FOUND);
    }

    @PostMapping("/admin/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("dto.parent: {}", categoryDTO.toString());

        return ResponseGenerator.generateResponseEntity(categoryService.create(categoryDTO), HttpStatus.OK);
    }

    @PutMapping("/admin/category/{id}/update")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {

        return ResponseGenerator.generateResponseEntity(categoryService.update(id, categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/admin/category/{id}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseGenerator.generateResponseEntity(HttpStatus.OK);
    }
}
