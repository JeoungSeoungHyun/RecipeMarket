package site.metacoding.recipemarket.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.service.UserService;
import site.metacoding.recipemarket.util.UtilValid;
import site.metacoding.recipemarket.web.dto.user.IdFindReqDto;
import site.metacoding.recipemarket.web.dto.user.JoinReqDto;
import site.metacoding.recipemarket.web.dto.user.PasswordResetReqDto;
import site.metacoding.recipemarket.web.dto.user.RememberReqDto;
import site.metacoding.recipemarket.web.dto.user.UserRespDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final HttpSession session;

    // 마이 페이지
    @GetMapping("/s/user/{userId}")
    public String myPageForm(@PathVariable Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {

        // 1. 회원정보
        UserRespDto resp = null; // 필요한 유저정보만 dto에 담을 것

        // 권한체크 (주소 userId와 세션 userId가 같은지 확인)
        if (userId == loginUser.getUser().getId()) {
            resp = userService.마이페이지(userId);
        } else {
            throw new CustomException("접근 권한이 없습니다.");
        }

        model.addAttribute("user", resp); // model에 user 정보 담기

        // 2. 즐겨찾기

        return "/user/myPageForm";
    }

    // 아이디 찾기 페이지
    @GetMapping("/find-username-form")
    public String idFindForm() {
        return "/user/findUsernameForm";
    }

    // 아이디 찾기 요청
    @PostMapping("/find-username")
    public String findusername(@Valid IdFindReqDto idFindReqDto, BindingResult bindingResult, Model model) {

        UtilValid.요청에러처리(bindingResult);
        String findUserId = userService.아이디찾기(idFindReqDto);
        model.addAttribute("findUserId", findUserId);
        return "/user/showIdForm";
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/reset-password-form")
    public String passwordResetForm() {
        return "/user/resetPasswordForm";
    }

    // 비밀번호 찾기 (임시 비밀번호 발급)
    @PostMapping("/reset-password")
    public String passwordReset(@Valid PasswordResetReqDto passwordResetReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);
        userService.임시패스워드발급(passwordResetReqDto);

        return "redirect:/login-form";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }

    // 메인 페이지
    @GetMapping("/")
    public String index() {
        return "/main";
    }

    // 쿠키 response에 담기
    @PostMapping("/remember")
    public @ResponseBody ResponseEntity<?> remember(@RequestBody RememberReqDto rememberReqDto) {

        String remember = rememberReqDto.getRemember();
        String username = rememberReqDto.getUsername();

        if (remember.equals("on")) {
            response.addHeader("Set-Cookie", "remember=" + username + ";path=/");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원가입 페이지
    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    // 로그인 페이지
    @GetMapping("/login-form")
    public String loginForm(Model model) {

        // 아이디 기억하기 (쿠키에 아이디를 담는 로직)
        Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName(); // 쿠키 이름 가져오기

                if (name.equals("remember")) { // cookie[i] 의 이름이 remember일 때
                    String value = cookies[i].getValue(); // 쿠키 값 가져오기
                    model.addAttribute("remember", value); // 쿠키의 값을 모델에 담아서
                }
            }
        }
        return "/user/loginForm";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/s/user/{userId}/update-form")
    public String updateForm() {
        return "/user/updateForm";
    }

}
