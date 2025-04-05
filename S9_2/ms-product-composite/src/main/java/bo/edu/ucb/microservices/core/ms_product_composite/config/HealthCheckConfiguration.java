package bo.edu.ucb.microservices.core.ms_product_composite.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bo.edu.ucb.microservices.core.ms_product_composite.external.MsProductIntegration;
import bo.edu.ucb.microservices.core.ms_product_composite.external.MsRecommendationIntegration;
import bo.edu.ucb.microservices.core.ms_product_composite.external.MsReviewIntegration;
import bo.edu.ucb.microservices.core.ms_product_composite.service.ProductCompositeService;

@Configuration
public class HealthCheckConfiguration {
	@Autowired
	MsProductIntegration productIntegration;
	
	@Autowired
	MsRecommendationIntegration recommendationIntegration;
	
	@Autowired
	MsReviewIntegration reviewIntegration;

	@Bean
	ReactiveHealthContributor coreServices() {

		final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>();

		registry.put("product", () -> productIntegration.getProductHealth());
		registry.put("recommendation", () -> recommendationIntegration.getRecommendationHealth());
		registry.put("review", () -> reviewIntegration.getReviewHealth());

		return CompositeReactiveHealthContributor.fromMap(registry);
	}
}
