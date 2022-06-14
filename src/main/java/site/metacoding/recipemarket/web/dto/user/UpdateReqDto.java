package site.metacoding.recipemarket.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateReqDto {

    // 한글, 영어 대소문자, 숫자만 허용
    @Pattern(regexp = "[가-힣a-zA-Z1-9]", message = "닉네임은 한글 자음, 특수문자가 들어갈 수 없습니다.")
    @Size(min = 1, max = 12, message = "닉네임은 최소 1자리, 최대 12자리까지 입력 가능합니다.")
    @NotBlank(message = "닉네임은 공백이 들어갈 수 없습니다.")
    private String nickname;

    // 영어 대소문자, 숫자, 특수문자만 허용
    @Pattern(regexp = "[a-zA-Z1-9~!@#$%^&*()_+|<>?:{}]", message = "비밀번호는 한글이 들어갈 수 없습니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 최소 8자리, 최대 16자리까지 입력 가능합니다.")
    @NotBlank(message = "비밀번호는 공백이 들어갈 수 없습니다.")
    private String password;

    @Size(min = 1, max = 60, message = "이메일은 최소 1자리, 최대 60자리까지 입력 가능합니다.")
    @NotBlank(message = "이메일은 공백이 들어갈 수 없습니다.")
    @Email
    private String email;
}
