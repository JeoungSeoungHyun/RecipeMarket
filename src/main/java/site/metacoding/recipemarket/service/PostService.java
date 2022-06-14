package site.metacoding.recipemarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.post.Post;
import site.metacoding.recipemarket.domain.post.PostRepository;
import site.metacoding.recipemarket.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostRespDto 게시물페이징 (Integer page){
        Pageable pageable = PageRequest.of(page, 12, Sort.by(Direction.DESC, "id"));

        Page<Post> postsEntity = postRepository.findAll(pageable);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        PostRespDto postRespDto = new PostRespDto(
            postsEntity, 
            postsEntity.getNumber() - 1, // 현재 페이지 번호 -1
            postsEntity.getNumber(), // 현재 페이지 번호
            postsEntity.getNumber() + 1, // 현재 페이지 번호 +1
            pageNumbers
        );


        return postRespDto;
    }
}
