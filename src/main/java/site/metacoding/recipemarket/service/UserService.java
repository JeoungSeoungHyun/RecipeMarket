package site.metacoding.recipemarket.service;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;
import site.metacoding.recipemarket.handler.ex.CustomApiException;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.util.UtilEmail;
import site.metacoding.recipemarket.util.UtilFileUpload;
import site.metacoding.recipemarket.web.dto.user.IdFindReqDto;
import site.metacoding.recipemarket.web.dto.user.PasswordResetReqDto;
import site.metacoding.recipemarket.web.dto.user.UpdateReqDto;
import site.metacoding.recipemarket.web.dto.user.UserRespDto;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UtilEmail utilEmail;

    public UserRespDto 마이페이지(Integer userId) {

        UserRespDto resp = new UserRespDto(); // 필요한 유저정보만 dto에 담을 것

        // 해당 유저 찾기
        Optional<User> userOp = userRepository.findById(userId);

        if (userOp.isPresent()) { // 유저가 있으면 dto에 옮겨담기
            User userEntity = userOp.get();
            resp.setUsername(userEntity.getUsername());
            resp.setNickname(userEntity.getNickname());
            resp.setEmail(userEntity.getEmail());
            resp.setProfileImg(userEntity.getProfileImg());
            return resp; // user정보가 담긴 dto 리턴
        } else { // 유저가 없으면 예외 처리
            throw new CustomException("해당 유저를 찾을 수 없습니다.");
        }
    }

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

    @Value("${file.path}")
    private String uploadFolder;

    // 아이디 중복 체크하기
    public boolean 아이디중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    // 이메일 중복 체크하기
    public boolean 이메일중복체크(String email) {
        Optional<User> userOp = userRepository.findByEmail(email);

        if (userOp.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    // 회원가입하기
    @Transactional // 트랜잭션 처리로 오류 발생 시 롤백
    public void 회원가입(User user) {
        // 비밀번호 해시 암호화
        String rawPassword = user.getPassword(); // 입력 받은 원본 패스워드
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 원본 패스워드에 해쉬 알고리즘 적용
        user.setPassword(encPassword); // 해시화된 패스워드로 값 변경

        userRepository.save(user);
    }

    // 회원 정보 수정하기
    @Transactional
    public User 회원수정(Integer id, UpdateReqDto updateReqDto) {

        Optional<User> userOp = userRepository.findById(id); // 영속화 (DB의 row를 영속성 컨텍스트에 옮김)

        if (userOp.isPresent()) {
            // 영속화된 오브젝트 수정
            User userEntity = userOp.get();

            String rawPassword = updateReqDto.getPassword(); // 회원 정보 수정에서 입력 받은 원본 패스워드
            String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해쉬 알고리즘 적용

            userEntity.setNickname(updateReqDto.getNickname());
            userEntity.setPassword(encPassword); // 해시화된 패스워드로 값 변경
            userEntity.setEmail(updateReqDto.getEmail());

            return userEntity;

        } else {
            throw new RuntimeException("회원수정에 실패하였습니다.");
        }

    } // 트랜잭션이 걸려있으면 @Service가 종료될 때 변경 감지 후 DB에 UPDATE -> 더티체킹

    // 프로파일 이미지 변경하기
    @Transactional
    public void 프로파일이미지변경(User principal, MultipartFile profileImgFile, HttpSession session) {
        // 1. 파일을 upload 폴더에 저장완료
        String profileImg = UtilFileUpload.write(uploadFolder, profileImgFile);

        // 2. 해당 경로를 User 테이블에 update 하면 됨.
        Optional<User> userOp = userRepository.findById(principal.getId());
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            userEntity.setProfileImg(profileImg);

            // 세션값 변경
            session.setAttribute("principal", userEntity);
        } else {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        }
    } // 영속화된 userEntity 변경 후 더티체킹완료됨.

    // 회원 탈퇴하기
    @Transactional
    public void 회원탈퇴(Integer id) {
        userRepository.deleteById(id);
    }
}
