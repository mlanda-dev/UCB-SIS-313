package bo.edu.ucb.microservicios.core.product.controller;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.microservicios.core.product.dto.ProductDto;

@RestController
@RequestMapping("/v1/product")
public class ProductServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceController.class);
	
	@GetMapping(value = "/{productId}", produces = "application/json")
	public ProductDto getProduct(@PathVariable("productId") int productId) {
		LOGGER.info("Obteniendo producto por el id: {}", productId);
		ProductDto productDto=new ProductDto();
		productDto.setId(productId);
		productDto.setName("Play 5");
		productDto.setDescription("Consola de juegos");
		productDto.setPrice(new BigDecimal("7000.00"));
		LOGGER.info("El producto con id {} se obtuvo de manera exitosa.",productId);
		return productDto;
	}
}
