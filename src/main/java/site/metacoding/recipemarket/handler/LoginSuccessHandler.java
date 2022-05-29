package site.metacoding.recipemarket.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import site.metacoding.recipemarket.config.auth.LoginUser;
import site.metacoding.recipemarket.domain.user.User;

// 시큐리티로 로그인 성공 시 작동 (시큐리티 안에 있는 세션 값을 세션에 넣어줌)
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User principal = loginUser.getUser();

        HttpSession session = request.getSession();
        session.setAttribute("principal", principal); // 세션에 시큐리티가 가지고 있는 loginUser 넣어주기
        response.sendRedirect("/");
    }

}
