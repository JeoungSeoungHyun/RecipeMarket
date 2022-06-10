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

    @GetMapping("reset-password-form")
    public String resetPasswordForm() {
        return "/user/resetPasswordForm";
    }

    @GetMapping("update-form")
    public String updateForm() {
        return "/user/updateForm";
    }

    @GetMapping("mypage-form")
    public String myPage() {
        return "/user/myPageForm";
    }

    @GetMapping("list-form")
    public String list() {
        return "/post/listForm";
    }

    @GetMapping("detail-form")
    public String detail() {
        return "/post/detailForm";
    }

}
