package bo.edu.ucb.microservices.core.msreview.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.microservices.dto.review.ReviewDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/review")
@Tag(name = "Review", description = "REST API para las reseñas de productos.")
public class ReviewServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceController.class);

	private final ServiceUtil serviceUtil;

	@Autowired
	public ReviewServiceController(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Operation(summary = "${api.review.get-review.description}", description = "${api.review.get-review.notes}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
			@ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}") })
	@GetMapping(produces = "application/json")
	public List<ReviewDto> getReviews(@RequestParam(value = "productId", required = true) int productId) {
		if (productId < 1) {
			throw new InvalidInputException("Id de producto inválido: " + productId);
		}

		if (productId == 213) {
			LOGGER.debug("No se encontraron reseñas para el producto con id: {}", productId);
			return new ArrayList<>();
		}

		List<ReviewDto> list = new ArrayList<>();
		list.add(new ReviewDto(productId, 1, "Author 1", "Subject 1", "Content 1", serviceUtil.getServiceAddress()));
		list.add(new ReviewDto(productId, 2, "Author 2", "Subject 2", "Content 2", serviceUtil.getServiceAddress()));
		list.add(new ReviewDto(productId, 3, "Author 3", "Subject 3", "Content 3", serviceUtil.getServiceAddress()));

		LOGGER.debug("Tamaño de la lista de reseñas: {}", list.size());

		return list;
	}
}
