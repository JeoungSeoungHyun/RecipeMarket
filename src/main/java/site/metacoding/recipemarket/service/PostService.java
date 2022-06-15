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
    public PostRespDto 게시물페이징(Integer page) {
        // 서브 페이지 번호(1~10 or 11~20 10개씩 구분)
        int subPage = page / 10;

        Pageable pageable = PageRequest.of(page, 12, Sort.by(Direction.DESC, "id"));

        Page<Post> postsEntity = postRepository.findAll(pageable);

        // 현재 페이지 번호
        Integer nowPage = postsEntity.getNumber() + 1;

        // 마지막 페이지 번호
        Integer lastPage = postsEntity.getTotalPages();
        System.out.println("마지막 페이지 번호 : " + lastPage);

        // 10개씩 페이지를 담는다.
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = (0 + (10 * subPage)); i < 10 * (1 + subPage); i++) {
            if (i + 1 > lastPage) {
                break;
            }
            pageNumbers.add(i + 1);
        }

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                nowPage - 1, // 현재 페이지 번호 -1
                nowPage, // 현재 페이지 번호
                nowPage + 1, // 현재 페이지 번호 +1
                pageNumbers);

        return postRespDto;
    }
}
