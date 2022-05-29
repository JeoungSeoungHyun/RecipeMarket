package site.metacoding.recipemarket.service;

import org.springframework.beans.factory.annotation.Value;
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

    // 파일 저장 경로
    @Value("${file.path}")
    private String uploadFolder;

    // !!!!이미지 오류 해결 필요!!!!
    @Transactional // 트랜잭션 처리로 오류 발생 시 롤백
    public void 회원가입(User user) {
        // 비밀번호 해시 암호화
        String rawPassword = user.getPassword(); // 입력 받은 원본 패스워드
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 원본 패스워드에 해쉬 알고리즘 적용
        user.setPassword(encPassword); // 해시화된 패스워드로 값 변경

        // profileImg UUID로 변경하여 경로를 저장하기
        // String profileImg = UtilFileUpload.write(uploadFolder, joinReqDto.getFile());
        // User user = joinReqDto.toEntity(profileImg);

        userRepository.save(user);
    }
}
