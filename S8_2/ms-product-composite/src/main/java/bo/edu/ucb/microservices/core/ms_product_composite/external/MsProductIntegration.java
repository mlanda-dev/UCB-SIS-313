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
public class MsProductIntegration {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsProductIntegration.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	private final String productServiceUrl;

	@Autowired
	public MsProductIntegration(RestTemplate restTemplate, ObjectMapper mapper,
			@Value("${app.product-service.host}") String productServiceHost,
			@Value("${app.product-service.port}") int productServicePort) {

		this.restTemplate = restTemplate;
		this.mapper = mapper;

		productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/v1/product";
	}
	
	public ProductDto getProduct(int productId) {

		try {
			String url = productServiceUrl + "/"+ productId;
			LOGGER.debug("Llamando a getProduct API en URL: {}", url);

			ProductDto product = restTemplate.getForObject(url, ProductDto.class);
			LOGGER.debug("Producto encontrado con id: {}", product.getProductId());

			return product;

		} catch (HttpClientErrorException ex) {
			throw handleHttpClientException(ex);
		}
	}

	

	public ProductDto createProduct(ProductDto body) {

		try {
			String url = productServiceUrl;
			LOGGER.debug("Se creará un nuevo producto en URL: {}", url);

			ProductDto product = restTemplate.postForObject(url, body, ProductDto.class);
			LOGGER.debug("Producto creado con id: {}", product.getProductId());

			return product;

		} catch (HttpClientErrorException ex) {
			throw handleHttpClientException(ex);
		}
	}

	public void deleteProduct(int productId) {
		try {
			String url = productServiceUrl + "/" + productId;
			LOGGER.debug("Llamará a deleteProduct API en URL: {}", url);

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
