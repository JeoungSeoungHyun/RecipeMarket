package site.metacoding.recipemarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.recipemarket.handler.LoginFailureHandler;
import site.metacoding.recipemarket.handler.LoginSuccessHandler;

@EnableWebSecurity // 시큐리티 활성화
@Configuration // IoC 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public BCryptPasswordEncoder encode() { // Password 해시화
        return new BCryptPasswordEncoder(); 
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 공격 방지 비활성화

        http.authorizeRequests() // http로부터 request를 받을 때,
                .antMatchers("/s/**").authenticated() // /s/ 주소로 접속하면 인증이 필요하다.
                .anyRequest().permitAll() // 모든 request의 권한을 허용한다.
                .and()
                .formLogin()
                .loginPage("/login-form") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/login") // 로그인 Form Action Url
                .successHandler(new LoginSuccessHandler()) // 로그인 성공 후 핸들러
                .failureHandler(new LoginFailureHandler()); // 로그인 실패 후 핸들러
    }
}
