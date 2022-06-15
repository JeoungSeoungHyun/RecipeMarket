package site.metacoding.recipemarket.domain.favorite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    // 상세보기 페이지에서 즐겨찾기 유무 확인하는 쿼리문
    @Query(value = "SELECT * FROM favorite WHERE userId = :userId AND postId = :postId", nativeQuery = true)
    Optional<Favorite> mFindByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
