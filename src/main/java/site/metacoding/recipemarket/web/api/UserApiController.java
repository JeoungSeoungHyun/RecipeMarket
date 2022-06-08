package site.metacoding.recipemarket.web.api;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.service.UserService;
import site.metacoding.recipemarket.web.dto.ResponseDto;
import site.metacoding.recipemarket.web.dto.user.UpdateReqDto;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    // DI
    private final UserService userService;
    private final HttpSession session;

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

    // 프로파일 이미지 수정
    @PutMapping("/s/api/user/profile-img")
    public ResponseEntity<?> profileImgUpdate(
            @AuthenticationPrincipal LoginUser loginUser,
            MultipartFile profileImgFile) {
        // 위에서 받은 id를 사용하면 세션값과 비교해서 권한체크를 해줘야 한다.
        // 그냥 세션값을 사용하면 권한체크 필요없음.

        // 세션값 변경
        userService.프로파일이미지변경(loginUser.getUser(), profileImgFile, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Put 요청은 Http Body가 있다. Http Header의 Content-Type에 MIME타입을 알려줘야 한다.
    // @RequestBody -> BufferedReader + JSON 파싱(자바 오브젝트)
    // @ResponseBody -> BufferedWriter + JSON 파싱(자바 오브젝트)
    // 회원 정보 수정
    @PutMapping("/s/api/user/{userId}")
    public @ResponseBody ResponseDto<?> userUpdate(@PathVariable Integer userId, @RequestBody UpdateReqDto updateReqDto) {

        User principal = (User) session.getAttribute("principal");

        // 권한체크
        if (principal.getId() != userId) {
            return new ResponseDto<String>(-1, "권한 없음", null);
        }

        User userEntity = userService.회원수정(userId, updateReqDto);
        session.setAttribute("principal", userEntity); // 세션변경 - 덮어쓰기

        return new ResponseDto<String>(1, "성공", null);
    }

    // 회원 탈퇴
    @DeleteMapping("/s/api/user/{userId}")
    public @ResponseBody ResponseDto<?> userDelete(@PathVariable Integer userId) {
        
        User principal = (User) session.getAttribute("principal");

        // 권한체크
        if (principal.getId() != userId) {
            return new ResponseDto<String>(-1, "권한 없음", null);
        }

        userService.회원탈퇴(userId);
        session.invalidate();

        return new ResponseDto<>(1, "성공", null);
    }

}
