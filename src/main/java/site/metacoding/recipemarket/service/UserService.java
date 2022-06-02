package site.metacoding.recipemarket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;
import site.metacoding.recipemarket.handler.ex.CustomApiException;
import site.metacoding.recipemarket.util.UtilFileUpload;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // 트랜잭션 처리로 오류 발생 시 롤백
    public void 회원가입(User user) {
        // 비밀번호 해시 암호화
        String rawPassword = user.getPassword(); // 입력 받은 원본 패스워드
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 원본 패스워드에 해쉬 알고리즘 적용
        user.setPassword(encPassword); // 해시화된 패스워드로 값 변경

        userRepository.save(user);
    }

    // 파일 저장 경로
    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 프로파일이미지변경(User user, MultipartFile profileImgFile) {
        // 1. 파일을 upload 폴더에 저장
        String profileImg = UtilFileUpload.write(uploadFolder, profileImgFile);

        // 2. 해당 경로를 User 테이블에 update 하면 됨.
        Optional<User> userOp = userRepository.findById(user.getId());
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            userEntity.setProfileImg(profileImg);
        } else {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        }
    } // 영속화된 userEntity 변경 후 더티체킹완료됨.

}
