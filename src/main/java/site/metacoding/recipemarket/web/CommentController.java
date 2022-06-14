package site.metacoding.recipemarket.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.domain.comment.Comment;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.service.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;

    // 상세보기 페이지에 댓글 등록하기
    @PostMapping("/s/post/{postId}/comment")
    public String write(@PathVariable Integer postId, Comment comment, @AuthenticationPrincipal LoginUser loginUser) {

        User principal = loginUser.getUser(); // 시큐리티 세션에 있는 유저정보를
        comment.setUser(principal); // comment의 유저에 담아서
        commentService.댓글쓰기(comment, postId); // 서비스로 보내기
        return "redirect:/post/" + postId;
    }
}
