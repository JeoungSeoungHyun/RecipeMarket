package site.metacoding.recipemarket.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRespDto {

    private String username;
    private String nickname;
    private String email;
    private String profileImg;
}
