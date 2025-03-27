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
public class MsReviewIntegration {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsReviewIntegration.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	private final String reviewServiceUrl;

	@Autowired
	public MsReviewIntegration(RestTemplate restTemplate, ObjectMapper mapper,
			@Value("${app.review-service.host}") String reviewServiceHost,
			@Value("${app.review-service.port}") int reviewServicePort) {

		this.restTemplate = restTemplate;
		this.mapper = mapper;

		reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/v1/review";
	}
	
	public List<ReviewDto> getReviews(int productId) {

		try {
			String url = reviewServiceUrl + "?productId="+ productId;

			LOGGER.debug("Llamando getReviews API en URL: {}", url);
			List<ReviewDto> reviews = restTemplate
					.exchange(url, GET, null, new ParameterizedTypeReference<List<ReviewDto>>() {
					}).getBody();

			LOGGER.debug("Se encontraron {} reseñas para el producto con id: {}", reviews.size(), productId);
			return reviews;

		} catch (Exception ex) {
			LOGGER.warn("Ocurrió una excepción al solicitar reseñas, retornando 0 reseñas: {}", ex.getMessage());
			return new ArrayList<>();
		}
	}

	public ReviewDto createReview(ReviewDto body) {

		try {
			String url = reviewServiceUrl;
			LOGGER.debug("Creará una reseña en URL: {}", url);

			ReviewDto review = restTemplate.postForObject(url, body, ReviewDto.class);
			LOGGER.debug("Reseña creada con id: {}", review.getProductId());

			return review;

		} catch (HttpClientErrorException ex) {
			throw handleHttpClientException(ex);
		}
	}

	public void deleteReviews(int productId) {
		try {
			String url = reviewServiceUrl + "?productId=" + productId;
			LOGGER.debug("Llamando a deleteReviews API en URL: {}", url);

			restTemplate.delete(url);

		} catch (HttpClientErrorException ex) {
			throw handleHttpClientException(ex);
		}
	}

	private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
		switch (HttpStatus.resolve(ex.getStatusCode().value())) {

		case NOT_FOUND:
			return new NotFoundException(getErrorMessage(ex));

		case UNPROCESSABLE_ENTITY:
			return new InvalidInputException(getErrorMessage(ex));

		default:
			LOGGER.warn("Se obtuvo un error HTTP: {}, se retornará la excepción", ex.getStatusCode());
			LOGGER.warn("Error body: {}", ex.getResponseBodyAsString());
			return ex;
		}
	}

	private String getErrorMessage(HttpClientErrorException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}
}
