package site.metacoding.recipemarket.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RememberReqDto {
    // 로그인할 때 아이디 기억하기 체크되어 있으면 username과 remember를 dto에 담아 전달
    private String username;
    private String remember;
}
