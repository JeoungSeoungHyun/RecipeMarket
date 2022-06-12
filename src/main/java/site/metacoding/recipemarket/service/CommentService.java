package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.comment.Comment;
import site.metacoding.recipemarket.domain.comment.CommentRepository;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.domain.post.PostRepository;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void 댓글쓰기(Comment comment, Integer postId) {

        Optional<Post> postOp = postRepository.findById(postId);

        if (postOp.isPresent()) { // 게시글 번호가 있으면
            Post postEntity = postOp.get(); // entity에 담아서
            comment.setPost(postEntity); // comment의 post에 담기
        } else {
            throw new RuntimeException("없는 게시글에 댓글을 작성할 수 없습니다");
        }

        commentRepository.save(comment); // 댓글 insert
    }
}
