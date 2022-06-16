package site.metacoding.recipemarket.web.dto.comment;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.recipemarket.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentReqDto {

    @NotBlank(message = "댓글은 공백일 수 없습니다.")
    @Lob
    private String content;

    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(content);
        return comment;
    }
}
