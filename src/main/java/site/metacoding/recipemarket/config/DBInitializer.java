package site.metacoding.recipemarket.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import site.metacoding.recipemarket.domain.user.User;
import site.metacoding.recipemarket.domain.user.UserRepository;

@Profile("test")
@Configuration
public class DBInitializer {
    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {

        return (args) -> {
            User principal = User.builder()
                    .id(1)
                    .username("ssar")
                    .nickname("ssarr")
                    .password("12341234")
                    .email("ssar@nate.com")
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            userRepository.save(principal);
        };
    }
}
