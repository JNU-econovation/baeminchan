package codesquad.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/join")
    public String joinForm() {
        return "/join";
    }

    @GetMapping("/member/join")
    public String joinForm_another() {
        return "redirect:/join";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/login";
    }
}
