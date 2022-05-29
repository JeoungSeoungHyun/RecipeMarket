package site.metacoding.recipemarket.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, toString
public class JoinReqDto {

    // 영어 대소문자, 숫자만 허용
    @Pattern(regexp = "[a-zA-Z1-9]{4,20}", message = "아이디는 한글, 공백, 특수문자가 들어갈 수 없습니다.")
    @Size(min = 4, max = 20, message = "아이디는 최소 4자리, 최대 20자리까지 입력 가능합니다.")
    @NotBlank // @NotNull, @NotEmpty 두개의 조합
    private String username;

    // 한글, 영어 대소문자, 숫자만 허용
    @Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z1-9]{1,12}", message = "닉네임은 공백, 특수문자가 들어갈 수 없습니다.")
    @Size(min = 1, max = 12, message = "닉네임은 최소 1자리, 최대 12자리까지 입력 가능합니다.")
    @NotBlank
    private String nickname;

    // 영어 대소문자, 숫자, 특수문자만 허용
    @Pattern(regexp = "[a-zA-Z1-9~!@#$%^&*()_+|<>?:{}]{8,16}", message = "비밀번호는 한글, 공백이 들어갈 수 없습니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 최소 8자리, 최대 16자리까지 입력 가능합니다.")
    @NotBlank
    private String password;

    @Size(min = 1, max = 60, message = "이메일은 최소 1자리, 최대 60자리까지 입력 가능합니다.")
    @NotBlank
    @Email
    private String email;

    // private MultipartFile file;

    private String profileImg; // 프로필 사진 할 때 지우고,

    public User toEntity() { // 여기 매개변수로 받기
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setEmail(email);
        user.setProfileImg(profileImg);
        return user;
    }
}
