package bo.edu.ucb.microservices.core.msreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("bo.edu.ucb.microservices")
public class MsReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReviewApplication.class, args);
	}

}
