package site.metacoding.recipemarket.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 비밀번호 찾기(임시 비밀번호 발급) 할 때 필요한 쿼리문
    @Query(value = "SELECT * FROM user WHERE username = :username AND email = :email", nativeQuery = true)
    Optional<User> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    // 시큐리티 로그인 시 유저네임 셀렉트
    // 아이디 중복체크
    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

    // 아이디 찾기 시 이메일 셀렉트
    // 이메일 중복체크
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
