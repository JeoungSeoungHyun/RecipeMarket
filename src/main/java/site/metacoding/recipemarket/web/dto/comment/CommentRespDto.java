package site.metacoding.recipemarket.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRespDto {
    private Comment comment; // 댓글
    private boolean auth; // 권한
}
