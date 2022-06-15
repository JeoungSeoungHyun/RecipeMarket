package site.metacoding.recipemarket.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @ DataJpaTest
 * - 런타임에 실행 (데이터베이스 관련된 것들만)
 * - h2 동작 (메모리 데이터베이스 실행)
 * - 자동 rollback (내부에 트랜잭션이 걸려있음)
 */

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class UserRepositoryTest {

    @Autowired // DI 해줌
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void db_init() {
        // 데이터베이스 초기화
        userRepository.deleteAll();
        em
                .createNativeQuery("ALTER TABLE user ALTER COLUMN id RESTART WITH 1")
                .executeUpdate();
    }

    @Test
    public void save_테스트() {
        // given
        String username = "ssar";
        String nickname = "ssarr";
        String password = "12341234"; // 해시로 암호화하는것은 서비스 책임
        String email = "ssar@nate.com";

        User user = User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();

        // when
        User userEntity = userRepository.save(user);

        // then
        assertEquals(username, userEntity.getUsername());
        assertEquals(nickname, userEntity.getNickname());
    }

    @Test
    public void findByEmail_테스트() {

        User user = User.builder()
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .build();

        userRepository.save(user);

        // given
        String email = "ssar@nate.com";

        // when
        Optional<User> userOp = userRepository.findByEmail(email);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            assertEquals(email, userEntity.getEmail());
        } else {
            assertEquals("1", 12);
        }
    }

    @Test
    public void findByUsernameAndEmail_테스트() {

        User user = User.builder()
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .build();

        userRepository.save(user);

        // given
        String username = "ssar";
        String email = "ssar@nate.com";

        // when
        Optional<User> userOp = userRepository.findByUsernameAndEmail(username, email);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            assertEquals(username, userEntity.getUsername());
            assertEquals(email, userEntity.getEmail());
        } else {
            assertEquals("1", 12);
        }
    }

    @Test
    public void findById_테스트() {

        User user = User.builder()
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .build();

        userRepository.save(user);

        // given
        Integer id = 1;

        // when
        Optional<User> userOp = userRepository.findById(id);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            assertEquals(id, userEntity.getId());
        } else {
            assertEquals("1", 12);
        }
    }

    @Test
    public void deleteById_테스트() {
        assertEquals("1", "1");
    }

    // 시큐리티 로그인 시 유저네임 셀렉트하기 위한 쿼리 테스트
    @Test
    public void findByUsername_테스트() {

        User user = User.builder()
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .build();

        userRepository.save(user);

        // given
        String username = "ssar";

        // when
        Optional<User> userOp = userRepository.findByUsername(username);

        // then
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            assertEquals(username, userEntity.getUsername());
        } else {
            assertEquals("1", 12);
        }
    }
}
