package site.metacoding.recipemarket.web.dto.post;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRespDto {
    private Page<Post> posts; // 모든 포스트
    private Integer prev; // 이전 페이지
    private Integer pageNumber; // 현재 페이지
    private Integer next; // 다음 페이지
    private List<Integer> pageNumbers; // 전체 페이지 수
}
