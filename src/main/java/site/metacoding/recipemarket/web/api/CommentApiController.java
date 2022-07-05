package site.metacoding.recipemarket.web.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.service.CommentService;
import site.metacoding.recipemarket.web.dto.comment.CommentReqDto;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    // 상세보기 페이지에서 댓글 수정하기
    @PutMapping("/s/api/comment/{id}")
    public ResponseEntity<?> updateById(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser,
            @Valid CommentReqDto dto, BindingResult bindingResult) {
        User principal = loginUser.getUser(); // 서비스에서 권한처리하려고 만듦
        commentService.댓글수정(id, principal, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 상세보기 페이지에서 댓글 삭제하기
    @DeleteMapping("/s/api/comment/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser) {
        User principal = loginUser.getUser(); // 서비스에서 권한처리하려고 만듦
        commentService.댓글삭제(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
