package codesquad.web;

import codesquad.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin")
    public String adminPage(Model model) {

        model.addAttribute("categoryList", categoryService.findAll());

        return "admin";
    }

    @GetMapping("/admin/category/{id}")
    public String categoryDetailPage(@PathVariable Long id, Model model) {

        model.addAttribute("category", categoryService.findById(id));

        return "category";
    }
}
