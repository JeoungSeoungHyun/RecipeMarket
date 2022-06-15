package site.metacoding.recipemarket.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.service.PostService;
import site.metacoding.recipemarket.web.dto.favorite.FavoriteRespDto;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    // 즐겨찾기 추가
    @PostMapping("/s/api/post/{postId}/favorite")
    public ResponseEntity<?> favorite(@PathVariable Integer postId, @AuthenticationPrincipal LoginUser loginUser) {
        FavoriteRespDto dto = postService.좋아요(postId, loginUser.getUser());
        System.out.println(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/s/api/post/{postId}/favorite/{favoriteId}")
    public ResponseEntity<?> unfavorite(@PathVariable Integer favoriteId,
            @AuthenticationPrincipal LoginUser loginUser) {
        postService.좋아요취소(favoriteId, loginUser.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
