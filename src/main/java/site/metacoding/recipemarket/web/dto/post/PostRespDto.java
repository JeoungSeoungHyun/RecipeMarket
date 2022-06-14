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
    private Page<Post> posts;
    private Integer prev;
    private Integer pageNumber;
    private Integer next;
    private List<Integer> pageNumbers;
}
