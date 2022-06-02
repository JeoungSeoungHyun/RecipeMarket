package site.metacoding.recipemarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("main")
    public String main() {
        return "/main";
    }

    @GetMapping("join-form")
    public String join() {
        return "/user/joinForm";
    }

    @GetMapping("login-form")
    public String login() {
        return "/user/loginForm";
    }

    @GetMapping("find-username-form")
    public String findUsernameForm() {
        return "/user/findUsernameForm";
    }

    @GetMapping("find-password-form")
    public String findPasswordForm() {
        return "/user/findPasswordForm";
    }

}
