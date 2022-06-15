package site.metacoding.recipemarket.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDetailRespDto {
    private Post post;
    private boolean isFavorite; // 즐겨찾기를 했으면 true, false
    private Integer favoriteId;
}
