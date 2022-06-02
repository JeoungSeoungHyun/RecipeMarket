package site.metacoding.recipemarket.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.service.UserService;
import site.metacoding.recipemarket.web.dto.user.JoinReqDto;

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

    @PutMapping("/api/user/{id}/profile-img")
    public ResponseEntity<?> profileImgUpdate(
            @AuthenticationPrincipal LoginUser loginUser,
            MultipartFile profileImgFile) {
        userService.프로파일이미지변경(loginUser.getUser(), profileImgFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인 페이지
    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }
}
