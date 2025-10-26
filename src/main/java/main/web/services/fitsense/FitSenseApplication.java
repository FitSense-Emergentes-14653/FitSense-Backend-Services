package main.web.services.fitsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitSenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitSenseApplication.class, args);
	}

}
