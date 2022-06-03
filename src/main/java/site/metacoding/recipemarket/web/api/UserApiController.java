package site.metacoding.recipemarket.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    // DI
    private final UserService userService;

    // 아이디 중복체크
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {

        boolean isNotSame = userService.아이디중복체크(username); // true (같지 않음)

        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

    // 이메일 중복체크
    @GetMapping("/api/user/email-same-check")
    public ResponseEntity<?> emailSameCheck(String email) {

        boolean isNotSame = userService.이메일중복체크(email); // true (같지 않음)

        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

}
