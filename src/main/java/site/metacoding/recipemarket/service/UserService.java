package site.metacoding.recipemarket.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.util.UtilEmail;
import site.metacoding.recipemarket.web.dto.user.IdFindReqDto;
import site.metacoding.recipemarket.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UtilEmail utilEmail;

    // 아이디 찾기
    @Transactional
    public String 아이디찾기(IdFindReqDto idFindReqDto) {
        // 1. email 이 같은 것이 있는지 체크 (DB)
        Optional<User> userOp = userRepository.findByEmail(idFindReqDto.getEmail());

        // 2. 같은 email이 있으면 DB에서 가져오기
        if (userOp.isPresent()) {
            User userEntity = userOp.get(); // 영속화
            return userEntity.getUsername(); // 아이디 리턴
        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void 임시패스워드발급(PasswordResetReqDto passwordResetReqDto) {

        String tempPw; // 임시 비밀번호 변수
        User userEntity; // 유저 엔티티 변수

        // 1. username, email 이 같은 것이 있는지 체크 (DB)
        Optional<User> userOp = userRepository.findByUsernameAndEmail(
                passwordResetReqDto.getUsername(),
                passwordResetReqDto.getEmail());

        // 2. 같은 것이 있다면 DB password 초기화 - BCrypt 해시 - update 하기 (DB)
        if (userOp.isPresent()) {
            // 회원정보 불러오기
            userEntity = userOp.get(); // 영속화

            // 임시 비밀번호 생성(UUID 이용)
            tempPw = UUID.randomUUID().toString().replace("-", ""); // -를 제거
            tempPw = tempPw.substring(0, 10); // tempPw를 앞에서부터 10자리까지 잘라줌

            // bCrypt에 임시 비밀번호를 인코딩해서 user 객체에 담기
            String encPassword = bCryptPasswordEncoder.encode(tempPw);
            userEntity.setPassword(encPassword);

        } else {
            throw new CustomException("해당 이메일이 존재하지 않습니다.");
        }

        // 3. 임시 비밀번호 이메일로 전송 (받는 사람, 제목, 내용)
        utilEmail.sendEmail(userEntity.getEmail(), "임시 비밀번호 발급", "임시 비밀번호 : " + tempPw);

    } // 더티체킹 (update)

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
