package site.metacoding.recipemarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.post.PostRepository;
import site.metacoding.recipemarket.service.PostService;
import site.metacoding.recipemarket.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    
    // 리스트 페이지
    @GetMapping("/post")
    public String listForm(Model model, @RequestParam(defaultValue = "1") Integer page) {
        PostRespDto postRespDto = postService.게시물페이징(page-1);
        model.addAttribute("data", postRespDto);
        long count = postRepository.count();
        model.addAttribute("count", count);

        return "/post/listForm";
    }
}
