package site.metacoding.recipemarket.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @ DataJpaTest
 * - 런타임에 실행 (데이터베이스 관련된 것들만)
 * - h2 동작 (메모리 데이터베이스 실행)
 * - 자동 rollback (내부에 트랜잭션이 걸려있음)
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class UserRepositoryTest {

    @Autowired // DI 해줌
    private UserRepository userRepository;

    // 시큐리티 로그인 시 유저네임 셀렉트하기 위한 쿼리 테스트
    @Test
    public void findByUsername_테스트() {
        // given - 가짜 데이터
        Integer id = 1;
        String username = "ssar";
        String nickname = "ssarr";
        String password = "12341234";
        String email = "ssar@nate.com";
        User user = User.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
        userRepository.save(user);

        // when - 테스트 진행
        Optional<User> userOp = userRepository.findByUsername(username);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            assertEquals(username, userEntity.getUsername());
            System.out.println("성공");
        } else {
            assertNotNull(userOp.get());
            System.out.println("실패");
        }

    }
}
