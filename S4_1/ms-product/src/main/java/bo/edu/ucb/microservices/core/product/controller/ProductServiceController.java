package bo.edu.ucb.microservices.core.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.microservices.dto.product.ProductDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.exceptions.NotFoundException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/product")
@Tag(name = "Product", description = "REST API para productos")
public class ProductServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceController.class);
	private final ServiceUtil serviceUtil;

	@Autowired
	public ProductServiceController(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Operation(summary = "${api.product.get-product.description}", description = "${api.product.get-product.notes}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
			@ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
			@ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")})
	@GetMapping(value = "/{productId}", produces = "application/json")
	public ProductDto getProduct(@Parameter(description = "${api.product.get-product.parameters.productId}", required = true) 
			@PathVariable("productId") int productId) {
		LOGGER.info("Obteniendo producto por el id: {}", productId);

		if (productId < 1) {
			throw new InvalidInputException("Id de producto inválido: " + productId);
		}

		if (productId == 13) {
			throw new NotFoundException("No se encontró producto con id: " + productId);
		}

		return new ProductDto(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
	}
}
