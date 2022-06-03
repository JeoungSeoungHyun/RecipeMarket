package site.metacoding.recipemarket.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 시큐리티 로그인 시 유저네임 셀렉트
    // 아이디 중복체크
    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    // 이메일 중복체크
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
