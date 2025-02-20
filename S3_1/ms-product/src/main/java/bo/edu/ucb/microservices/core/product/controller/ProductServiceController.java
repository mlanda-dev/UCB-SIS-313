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

@RestController
@RequestMapping(value="/v1/product")
public class ProductServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceController.class);
	private final ServiceUtil serviceUtil;

	@Autowired
	public ProductServiceController(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	public ProductDto getProduct(@PathVariable("productId") int productId) {
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
