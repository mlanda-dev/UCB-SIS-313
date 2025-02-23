package bo.edu.ucb.microservices.dto.productcomposite;

import java.util.List;

public class ProductAggregateDto {
	private int productId;
	private String name;
	private int weight;
	private List<RecommendationSummaryDto> recommendations;
	private List<ReviewSummaryDto> reviews;
	private ServiceAddressesDto serviceAddresses;

	public ProductAggregateDto() {

	}

	public ProductAggregateDto(int productId, String name, int weight, List<RecommendationSummaryDto> recommendations,
			List<ReviewSummaryDto> reviews, ServiceAddressesDto serviceAddresses) {
		super();
		this.productId = productId;
		this.name = name;
		this.weight = weight;
		this.recommendations = recommendations;
		this.reviews = reviews;
		this.serviceAddresses = serviceAddresses;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<RecommendationSummaryDto> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<RecommendationSummaryDto> recommendations) {
		this.recommendations = recommendations;
	}

	public List<ReviewSummaryDto> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewSummaryDto> reviews) {
		this.reviews = reviews;
	}

	public ServiceAddressesDto getServiceAddresses() {
		return serviceAddresses;
	}

	public void setServiceAddresses(ServiceAddressesDto serviceAddresses) {
		this.serviceAddresses = serviceAddresses;
	}

	@Override
	public String toString() {
		return "ProductAggregateDto [productId=" + productId + ", name=" + name + ", weight=" + weight
				+ ", recommendations=" + recommendations + ", reviews=" + reviews + ", serviceAddresses="
				+ serviceAddresses + "]";
	}
}