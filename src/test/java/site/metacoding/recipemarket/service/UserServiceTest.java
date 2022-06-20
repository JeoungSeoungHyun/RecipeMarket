package site.metacoding.recipemarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import site.metacoding.recipemarket.config.MailConfig;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;
import site.metacoding.recipemarket.util.UtilEmail;
import site.metacoding.recipemarket.web.dto.user.IdFindReqDto;
import site.metacoding.recipemarket.web.dto.user.PasswordResetReqDto;
import site.metacoding.recipemarket.web.dto.user.UpdateReqDto;
import site.metacoding.recipemarket.web.dto.user.UserRespDto;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) // Mockito 컨테이너 생성, IoC 컨테이너 내부에 있는 것처럼 속이기
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepository;

    @Spy
    private MailConfig mailConfig = new MailConfig();

    @Spy
    private UtilEmail utilEmail = new UtilEmail(mailConfig.getMailSender());

    @Test
    public void 마이페이지_테스트() {
        // given
        Integer id = 1;

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findById(id)).thenReturn(userOp);

        // when
        UserRespDto dto = userService.마이페이지(id);

        // then
        assertEquals(userEntity.getUsername(), dto.getUsername());
        assertEquals(userEntity.getNickname(), dto.getNickname());
    }

    @Test
    public void 아이디찾기_테스트() {
        // given
        IdFindReqDto dto = new IdFindReqDto("ssar@nate.com");

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findByEmail(dto.getEmail())).thenReturn(userOp);

        // when
        String username = userService.아이디찾기(dto);

        // then
        assertEquals(userEntity.getUsername(), username);
    }

    // sendEmail 테스트 미완료
    @Test
    public void 임시패스워드발급_테스트() {
        // given
        String username = "ssar";
        String email = "xldzjqpf1588@naver.com";

        PasswordResetReqDto dto = new PasswordResetReqDto(username, email);

        // stub
        User givenUser = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("xldzjqpf1588@naver.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(givenUser);
        Mockito.when(userRepository.findByUsernameAndEmail(username, email)).thenReturn(userOp);

        // doNothing().when(utilEmail).sendEmail("", "", "");

        // when
        User userEntity = userService.임시패스워드발급(dto);

        // then
        assertEquals(email, userEntity.getEmail());
    }

    @Test
    public void 아이디중복체크() {
        // given
        String username = "ssar";

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(userOp);

        // when
        boolean usernameCheck = userService.아이디중복체크(username);

        // then
        assertEquals(false, usernameCheck);
    }

    @Test
    public void 이메일중복체크() {
        // given
        String email = "ssar@nate.com";

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(userOp);

        // when
        boolean usernameCheck = userService.이메일중복체크(email);

        // then
        assertEquals(false, usernameCheck);
    }

    @Test
    public void 회원가입() {
        // given
        User givenUser = User.builder()
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .build();

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password(bCryptPasswordEncoder.encode(givenUser.getPassword()))
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.save(givenUser)).thenReturn(userEntity);

        // when
        // User userEntity2 = userService.회원가입(givenUser); // 회원가입 리턴타입 바꿔서 테스트 완료
        userService.회원가입(givenUser);

        // then
        assertEquals(givenUser.getUsername(), userEntity.getUsername());
    }

    @Test
    public void 회원수정() {
        // given
        Integer id = 1;

        String nickname = "cosss";
        String password = "1234512345";
        String email = "cos@nate.com";

        UpdateReqDto dto = new UpdateReqDto(nickname, password, email);

        // stub
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findById(id)).thenReturn(userOp);

        // when
        User userEntity2 = userService.회원수정(id, dto);

        // then
        assertEquals(dto.getNickname(), userEntity2.getNickname());
        assertEquals(dto.getEmail(), userEntity2.getEmail());
    }

    @Test
    public void 프로파일이미지변경() throws IOException {
        // given
        User userEntity = User.builder()
                .id(1)
                .username("ssar")
                .nickname("ssarr")
                .password("12341234")
                .email("ssar@nate.com")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        File file = new File(
                "C:\\Users\\green\\upload\\dog.jpg");
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false,
                file.getName(),
                (int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        MockHttpSession session = new MockHttpSession();

        // stub
        Optional<User> userOp = Optional.of(userEntity);
        Mockito.when(userRepository.findById(userEntity.getId())).thenReturn(userOp);

        // when
        User userEntity2 = userService.프로파일이미지변경(userEntity, multipartFile, session);

        // then
        assertNotNull(userEntity2.getProfileImg());
    }

    public void 회원탈퇴() {
        // 레파지토리에서 테스트 완료
    }
}
