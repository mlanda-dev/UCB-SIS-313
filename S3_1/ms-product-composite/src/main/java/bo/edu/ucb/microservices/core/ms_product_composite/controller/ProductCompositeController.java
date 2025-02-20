package bo.edu.ucb.microservices.core.ms_product_composite.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.microservices.core.ms_product_composite.external.ProductCompositeIntegration;
import bo.edu.ucb.microservices.dto.product.ProductDto;
import bo.edu.ucb.microservices.dto.productcomposite.ProductAggregateDto;
import bo.edu.ucb.microservices.dto.productcomposite.RecommendationSummaryDto;
import bo.edu.ucb.microservices.dto.productcomposite.ReviewSummaryDto;
import bo.edu.ucb.microservices.dto.productcomposite.ServiceAddressesDto;
import bo.edu.ucb.microservices.dto.recommendation.RecommendationDto;
import bo.edu.ucb.microservices.dto.review.ReviewDto;
import bo.edu.ucb.microservices.util.exceptions.NotFoundException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;

@RestController
@RequestMapping(value = "/v1/product-composite")
public class ProductCompositeController {

	private final ServiceUtil serviceUtil;
	private ProductCompositeIntegration integration;

	@Autowired
	public ProductCompositeController(ServiceUtil serviceUtil, ProductCompositeIntegration integration) {
		this.serviceUtil = serviceUtil;
		this.integration = integration;
	}

	@GetMapping(value = "/{productId}", produces = "application/json")
	public ProductAggregateDto getProduct(@PathVariable int productId) {
		ProductDto product = integration.getProduct(productId);
		if (product == null) {
			throw new NotFoundException("Ningún producto encontrado para el id: " + productId);
		}

		List<RecommendationDto> recommendations = integration.getRecommendations(productId);

		List<ReviewDto> reviews = integration.getReviews(productId);

		return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
	}

	private ProductAggregateDto createProductAggregate(ProductDto product, List<RecommendationDto> recommendations,
			List<ReviewDto> reviews, String serviceAddress) {

		// 1. Setup product info
		int productId = product.getProductId();
		String name = product.getName();
		int weight = product.getWeight();

		// 2. Copy summary recommendation info, if available
		List<RecommendationSummaryDto> recommendationSummaries = (recommendations == null) ? null
				: recommendations.stream()
						.map(r -> new RecommendationSummaryDto(r.getRecommendationId(), r.getAuthor(), r.getRate()))
						.collect(Collectors.toList());

		// 3. Copy summary review info, if available
		List<ReviewSummaryDto> reviewSummaries = (reviews == null) ? null
				: reviews.stream().map(r -> new ReviewSummaryDto(r.getReviewId(), r.getAuthor(), r.getSubject()))
						.collect(Collectors.toList());

		// 4. Create info regarding the involved microservices addresses
		String productAddress = product.getServiceAddress();
		String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
		String recommendationAddress = (recommendations != null && recommendations.size() > 0)
				? recommendations.get(0).getServiceAddress()
				: "";
		ServiceAddressesDto serviceAddresses = new ServiceAddressesDto(serviceAddress, productAddress, reviewAddress,
				recommendationAddress);

		return new ProductAggregateDto(productId, name, weight, recommendationSummaries, reviewSummaries,
				serviceAddresses);
	}

}
