package bo.edu.ucb.microservices.core.ms_product_composite.external;

import static org.springframework.http.HttpMethod.GET;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import bo.edu.ucb.microservices.dto.product.ProductDto;
import bo.edu.ucb.microservices.dto.recommendation.RecommendationDto;
import bo.edu.ucb.microservices.dto.review.ReviewDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.exceptions.NotFoundException;
import bo.edu.ucb.microservices.util.http.HttpErrorInfo;

@Component
public class ProductCompositeIntegration {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCompositeIntegration.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	private final String productServiceUrl;
	private final String recommendationServiceUrl;
	private final String reviewServiceUrl;

	@Autowired
	public ProductCompositeIntegration(RestTemplate restTemplate, ObjectMapper mapper,
			@Value("${app.product-service.host}") String productServiceHost,
			@Value("${app.product-service.port}") int productServicePort,
			@Value("${app.recommendation-service.host}") String recommendationServiceHost,
			@Value("${app.recommendation-service.port}") int recommendationServicePort,
			@Value("${app.review-service.host}") String reviewServiceHost,
			@Value("${app.review-service.port}") int reviewServicePort) {

		this.restTemplate = restTemplate;
		this.mapper = mapper;

		productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/v1/product/";
		recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort
				+ "/v1/recommendation?productId=";
		reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/v1/review?productId=";
	}

	public ProductDto getProduct(int productId) {

		try {
			String url = productServiceUrl + productId;
			LOGGER.debug("Llamando a getProduct API en URL: {}", url);

			ProductDto product = restTemplate.getForObject(url, ProductDto.class);
			LOGGER.debug("Producto encontrado con id: {}", product.getProductId());

			return product;

		} catch (HttpClientErrorException ex) {

			switch (HttpStatus.resolve(ex.getStatusCode().value())) {
			case NOT_FOUND:
				throw new NotFoundException(getErrorMessage(ex));

			case UNPROCESSABLE_ENTITY:
				throw new InvalidInputException(getErrorMessage(ex));

			default:
				LOGGER.warn("Error HTTP inesperado: {}", ex.getStatusCode());
				LOGGER.warn("Descripción del error: {}", ex.getResponseBodyAsString());
				throw ex;
			}
		}
	}

	private String getErrorMessage(HttpClientErrorException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}

	public List<RecommendationDto> getRecommendations(int productId) {

		try {
			String url = recommendationServiceUrl + productId;

			LOGGER.debug("Will call getRecommendations API on URL: {}", url);
			List<RecommendationDto> recommendations = restTemplate
					.exchange(url, GET, null, new ParameterizedTypeReference<List<RecommendationDto>>() {
					}).getBody();

			LOGGER.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
			return recommendations;

		} catch (Exception ex) {
			LOGGER.warn("Got an exception while requesting recommendations, return zero recommendations: {}",
					ex.getMessage());
			return new ArrayList<>();
		}
	}

	public List<ReviewDto> getReviews(int productId) {

		try {
			String url = reviewServiceUrl + productId;

			LOGGER.debug("Will call getReviews API on URL: {}", url);
			List<ReviewDto> reviews = restTemplate
					.exchange(url, GET, null, new ParameterizedTypeReference<List<ReviewDto>>() {
					}).getBody();

			LOGGER.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
			return reviews;

		} catch (Exception ex) {
			LOGGER.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
			return new ArrayList<>();
		}
	}
}
