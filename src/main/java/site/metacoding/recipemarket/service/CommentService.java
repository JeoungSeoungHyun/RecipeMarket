package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.comment.Comment;
import site.metacoding.recipemarket.domain.comment.CommentRepository;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.domain.post.PostRepository;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.handler.ex.CustomException;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void 댓글삭제(Integer id, User principal) {
        Optional<Comment> commentOp = commentRepository.findById(id); // 댓글 id 찾아서

        if (commentOp.isPresent()) { // id가 존재하면
            if (principal.getId() != commentOp.get().getUser().getId()) { // 세션 id와 디비에 있는 id가 다르면
                throw new CustomException("삭제 권한이 없습니다."); // 권한 X
            }
        } else { // id가 존재하지 않으면 예외처리
            throw new RuntimeException("해당 댓글이 없습니다");
        }
        // 세션 id와 디비 id가 같으면 댓글 삭제
        commentRepository.deleteById(id);
    }

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
