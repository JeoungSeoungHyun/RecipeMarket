package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.favorite.Favorite;
import site.metacoding.recipemarket.domain.favorite.FavoriteRepository;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.domain.post.PostRepository;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.handler.ex.CustomApiException;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.web.dto.favorite.FavoriteRespDto;
import site.metacoding.recipemarket.web.dto.favorite.FavoriteRespDto.PostDto;
import site.metacoding.recipemarket.web.dto.post.PostDetailRespDto;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public FavoriteRespDto 즐겨찾기추가(Integer postId, User principal) {

        Optional<Post> postOp = postRepository.findById(postId);
        if (postOp.isEmpty()) {
            throw new CustomApiException("해당되는 게시글이 없습니다.");
        }
        Post postEntity = postOp.get();

        Favorite favorite = new Favorite();
        favorite.setUser(principal);
        favorite.setPost(postEntity);

        Favorite favoriteEntity = favoriteRepository.save(favorite);

        FavoriteRespDto resp = new FavoriteRespDto();
        resp.setFavoriteId(favoriteEntity.getId());
        PostDto postDto = resp.new PostDto();
        postDto.setTitle(postEntity.getTitle());
        resp.setPost(postDto);
        return resp;
    }

    @Transactional
    public void 즐겨찾기취소(Integer favoriteId, User principal) {
        Optional<Favorite> favoriteOp = favoriteRepository.findById(favoriteId); // 즐겨찾기 번호 찾아서
        if (favoriteOp.isEmpty()) {
            throw new CustomApiException("요청하신 정보를 찾을 수 없습니다.");
        }
        Favorite favoriteEntity = favoriteOp.get();

        // 권한체크
        if (favoriteEntity.getUser().getId() == principal.getId()) {
            favoriteRepository.deleteById(favoriteId); // 해당 번호 즐겨찾기 정보 삭제
        }
    }

    // 로그인 없이 글 상세보기
    @Transactional
    public PostDetailRespDto 글상세보기(Integer postId) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 찾기
        Optional<Post> postOp = postRepository.findById(postId);

        if (postOp.isPresent()) { // 존자하는 게시글이면
            Post postEntity = postOp.get();

            // 리턴값 만들기
            postDetailRespDto.setPost(postEntity);
            postDetailRespDto.setFavorite(false); // 즐겨찾기 유무 추가하기
            postDetailRespDto.setFavoriteId(0);

            return postDetailRespDto;
        } else {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다");
        }
    }

    // 로그인한 유저가 보는 글 상세보기
    @Transactional
    public PostDetailRespDto 글상세보기(Integer postId, User principal) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 찾기
        Optional<Post> postOp = postRepository.findById(postId);

        if (postOp.isEmpty()) { // 존재하지 않는 게시글이면 익셉션처리
            throw new CustomException("존재하지 않는 게시글입니다.");
        }

        // 리턴값 만들기
        Post postEntity = postOp.get();
        postDetailRespDto.setPost(postEntity);

        // 즐겨찾기 유무 확인 후 리턴값 만들기
        // 로그인한 사람의 userId와 상세보기한 postId로 Favorite 테이블에서 select해서 row가 있으면 true
        Optional<Favorite> favoriteOp = favoriteRepository.mFindByUserIdAndPostId(principal.getId(), postId);
        if (favoriteOp.isPresent()) {
            Favorite favoriteEntity = favoriteOp.get();
            postDetailRespDto.setFavoriteId(favoriteEntity.getId());
            postDetailRespDto.setFavorite(true);
        } else {
            postDetailRespDto.setFavoriteId(0);
            postDetailRespDto.setFavorite(false);
        }
        return postDetailRespDto;
    }
}
