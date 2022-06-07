package site.metacoding.recipemarket.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.service.UserService;
import site.metacoding.recipemarket.util.UtilValid;
import site.metacoding.recipemarket.web.dto.user.JoinReqDto;
import site.metacoding.recipemarket.web.dto.user.RememberReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final HttpSession session;

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
        return "/index";
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
            String name = cookies[0].getName(); // 쿠키 이름 가져오기
            String value = cookies[0].getValue(); // 쿠키 값 가져오기0

            if (name.equals("remember")) { // name이 remember 이면
                model.addAttribute("remember", value); // name의 값을 모델에 담아서
                return "/user/loginForm"; // 뷰로 가져가기
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
