package site.metacoding.recipemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RecipemarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipemarketApplication.class, args);
	}

}
