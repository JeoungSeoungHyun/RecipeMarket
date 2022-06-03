package site.metacoding.recipemarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;

    // 회원가입 페이지
    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    // 로그인 페이지
    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }
}
