package bo.edu.ucb.microservices.core.msreview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("bo.edu.ucb.microservices")
public class MsReviewApplication {
	
	private static Logger LOGGER =LoggerFactory.getLogger(MsReviewApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MsReviewApplication.class, args);
		
	    String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");

	    LOGGER.info("Connected to PostgreSQL: " + mysqlUri);
	}

}
