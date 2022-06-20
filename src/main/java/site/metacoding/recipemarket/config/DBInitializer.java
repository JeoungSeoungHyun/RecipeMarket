package site.metacoding.recipemarket.config;

import java.util.Locale.Category;

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
                    .username("ssar")
                    .password("1234")
                    .email("xldzjqpf1588@naver.com")
                    .build();

            userRepository.save(principal);
        };
    }
}
