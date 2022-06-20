package site.metacoding.recipemarket.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test") // test 설정파일로 실행 -> h2
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context) // spring의 환경을 알고있어야 set할텐데
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public void myPageForm_테스트() {
    }

    public void idFindForm_테스트() {
    }

    public void findusername_테스트() {
    }

    public void passwordResetForm_테스트() {
    }

    public void passwordReset_테스트() {
    }

    public void join_테스트() {
    }

    public void index_테스트() {
    }

    public void remember_테스트() {
    }

    public void joinForm_테스트() {
    }

    public void loginForm_테스트() {
    }

    public void updateForm_테스트() {
    }

}
