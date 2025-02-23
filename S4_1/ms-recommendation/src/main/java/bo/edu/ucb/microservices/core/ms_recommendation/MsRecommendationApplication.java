package bo.edu.ucb.microservices.core.ms_recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("bo.edu.ucb.microservices")
public class MsRecommendationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRecommendationApplication.class, args);
	}

}
