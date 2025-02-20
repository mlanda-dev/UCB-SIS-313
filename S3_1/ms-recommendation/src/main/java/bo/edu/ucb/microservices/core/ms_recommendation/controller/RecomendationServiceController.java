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

@RestController
@RequestMapping(value = "/v1/recommendation")
public class RecomendationServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecomendationServiceController.class);

	private final ServiceUtil serviceUtil;

	@Autowired
	public RecomendationServiceController(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@GetMapping(produces = "application/json")
	public List<RecommendationDto> getRecommendations(@RequestParam(value = "productId", required = true) int productId) {
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
