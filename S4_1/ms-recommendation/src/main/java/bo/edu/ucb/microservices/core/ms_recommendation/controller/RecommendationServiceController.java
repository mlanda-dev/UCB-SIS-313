package bo.edu.ucb.microservices.core.ms_recommendation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.microservices.dto.recommendation.RecommendationDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/recommendation")
@Tag(name = "Recommendation", description = "REST API para las recomendaciones de productos.")
public class RecommendationServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationServiceController.class);

	private final ServiceUtil serviceUtil;

	@Autowired
	public RecommendationServiceController(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Operation(summary = "${api.recommendation.get-recommendation.description}", description = "${api.recommendation.get-recommendation.notes}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
			@ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}") })
	@GetMapping(produces = "application/json")
	public List<RecommendationDto> getRecommendations(@Parameter(description = "${api.recommendation.get-recommendation.parameters.productId}", required = true) 
			@RequestParam(value = "productId", required = true) int productId) {
		if (productId < 1) {
			throw new InvalidInputException("Id de producto inválido: " + productId);
		}

		if (productId == 113) {
			LOGGER.debug("No se encontraron recomendacones para el producto con id: {}", productId);
			return new ArrayList<>();
		}

		List<RecommendationDto> list = new ArrayList<>();
		list.add(new RecommendationDto(productId, 1, "Author 1", 1, "Content 1", serviceUtil.getServiceAddress()));
		list.add(new RecommendationDto(productId, 2, "Author 2", 2, "Content 2", serviceUtil.getServiceAddress()));
		list.add(new RecommendationDto(productId, 3, "Author 3", 3, "Content 3", serviceUtil.getServiceAddress()));

		LOGGER.debug("Tamaño de la lista de recomendaciones: {}", list.size());

		return list;
	}
}
