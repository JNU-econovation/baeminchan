package codesquad.web;

import codesquad.sequrity.HttpSessionUtils;
import codesquad.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PromotionService promotionService;

    @GetMapping("/member/join")
    public String joinForm_another() {
        return "/join";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/member/find")
    public String findIdForm() {
        return "/find-id";
    }

    @GetMapping("/member/find-pass")
    public String findPasswordForm() {
        return "/find-password";
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("subDishList", promotionService.findSubDishList());
        model.addAttribute("mainDishList", promotionService.findMainDishList());
        model.addAttribute("soupAndStewDishList", promotionService.findSoupAndStewList());

        return "/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.SESSIONED_USER);

        return "redirect:/login";
    }
}
