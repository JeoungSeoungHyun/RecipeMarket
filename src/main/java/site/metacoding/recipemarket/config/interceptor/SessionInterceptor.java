package site.metacoding.recipemarket.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import site.metacoding.recipemarket.config.auth.LoginUser;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        boolean isLogin = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (isLogin) {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            HttpSession session = request.getSession(); // 세션 가져오기
            session.setAttribute("principal", loginUser.getUser());
        }

        return true;
    }
}