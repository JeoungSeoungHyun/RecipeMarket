package site.metacoding.recipemarket.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdFindReqDto {

    @Size(min = 1, max = 60)
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
