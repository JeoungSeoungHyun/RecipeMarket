package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean 아이디중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean 이메일중복체크(String email) {
        Optional<User> userOp = userRepository.findByEmail(email);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional // 트랜잭션 처리로 오류 발생 시 롤백
    public void 회원가입(User user) {
        // 비밀번호 해시 암호화
        String rawPassword = user.getPassword(); // 입력 받은 원본 패스워드
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 원본 패스워드에 해쉬 알고리즘 적용
        user.setPassword(encPassword); // 해시화된 패스워드로 값 변경

        userRepository.save(user);
    }

}
