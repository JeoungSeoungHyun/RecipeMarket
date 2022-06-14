package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.domain.post.PostRepository;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post 글상세보기(Integer postId) {
        Optional<Post> postOp = postRepository.findById(postId);

        if (postOp.isPresent()) {
            return postOp.get();
        } else {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다");
        }
    }
}
