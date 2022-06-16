package site.metacoding.recipemarket.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDetailRespDto { // 상세보기 페이지에서 필요한 데이터 dto에 담기
    private Post post; // 게시글
    private boolean isFavorite; // 즐겨찾기를 했으면 true, false
    private Integer favoriteId; // 즐겨찾기 ID
}
