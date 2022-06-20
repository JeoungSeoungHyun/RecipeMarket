package site.metacoding.recipemarket.config.auth;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;

@Profile("dev")
@RequiredArgsConstructor
@Service // 메모리에 띄우기 (IoC 컨테이너에 등록됨)
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) { // username이 있으면
            return new LoginUser(userOp.get()); // 시큐리티 세션에 user 넣어줌
        }

        // username이 없으면
        return null; // principal에 null 들어감 -> 인증실패
    }

}
