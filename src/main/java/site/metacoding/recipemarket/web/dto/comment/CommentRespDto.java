package site.metacoding.recipemarket.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRespDto {
    private Comment comment; // ëę¸
    private boolean auth; // ęśí
}
