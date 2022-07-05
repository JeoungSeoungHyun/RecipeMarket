package site.metacoding.recipemarket.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.domain.comment.Comment;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.service.PostService;
import site.metacoding.recipemarket.web.dto.comment.CommentRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public String detail(@PathVariable Integer postId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
        Post postEntity = postService.글상세보기(postId);

        // 댓글별로 수정/삭제 권한이 있는지 확인하기 위해 for문으로 auth 체크
        List<CommentRespDto> comments = new ArrayList<>();

        for (Comment comment : postEntity.getComments()) {
            CommentRespDto dto = new CommentRespDto();
            dto.setComment(comment); // 엔티티의 댓글 dto에 담아주기

            if (loginUser != null) { // 세션이 있으면
                if (loginUser.getUser().getId() == comment.getUser().getId()) { // 세션id와 dto의 userId가 같으면
                    dto.setAuth(true); // auth => true
                } else { // 세션id와 dto의 userId가 다르면
                    dto.setAuth(false); // auth => false
                }
            } else { // 세션 없으면
                dto.setAuth(false); // auth => false
            }
            comments.add(dto); // list에 담기
        }

        model.addAttribute("comments", comments); // 댓글
        return "/post/detailForm";
    }
}
