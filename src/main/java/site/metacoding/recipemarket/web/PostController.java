package site.metacoding.recipemarket.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.service.PostService;
import site.metacoding.recipemarket.web.dto.post.PostDetailRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    // 글 상세보기
    @GetMapping("/post/{id}")
    public String detail(@PathVariable Integer id, Model model, @AuthenticationPrincipal LoginUser loginUser) {

        PostDetailRespDto postDetailRespDto = null;

        if (loginUser == null) { // 로그인 안 했을 경우
            postDetailRespDto = postService.글상세보기(id);
        } else { // 로그인 했을 경우
            postDetailRespDto = postService.글상세보기(id, loginUser.getUser());
        }

        model.addAttribute("data", postDetailRespDto);

        return "/post/detailForm";
    }
}
