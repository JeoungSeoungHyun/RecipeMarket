package site.metacoding.recipemarket.web.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteRespDto {
    private Integer favoriteId;
    private PostDto post;

    @Data
    public class PostDto {
        private Integer postId;
        private String title;
    }
}
