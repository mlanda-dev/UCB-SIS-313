package bo.edu.ucb.microservices.dto.productcomposite;

public class RecommendationSummaryDto {
	private int recommendationId;
	private String author;
	private int rate;

	public RecommendationSummaryDto() {

	}

	public RecommendationSummaryDto(int recommendationId, String author, int rate) {
		super();
		this.recommendationId = recommendationId;
		this.author = author;
		this.rate = rate;
	}

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "RecommendationSummaryDto [recommendationId=" + recommendationId + ", author=" + author + ", rate="
				+ rate + "]";
	}
}
