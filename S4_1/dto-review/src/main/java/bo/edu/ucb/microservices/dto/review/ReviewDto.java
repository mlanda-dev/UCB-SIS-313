package bo.edu.ucb.microservices.dto.review;

public class ReviewDto {

	private int productId;
	private int reviewId;
	private String author;
	private String subject;
	private String content;
	private String serviceAddress;

	public ReviewDto() {

	}

	public ReviewDto(int productId, int reviewId, String author, String subject, String content,
			String serviceAddress) {
		super();
		this.productId = productId;
		this.reviewId = reviewId;
		this.author = author;
		this.subject = subject;
		this.content = content;
		this.serviceAddress = serviceAddress;
	}

	public int getProductId() {
		return productId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public String getAuthor() {
		return author;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	@Override
	public String toString() {
		return "ReviewDto [productId=" + productId + ", reviewId=" + reviewId + ", author=" + author + ", subject="
				+ subject + ", content=" + content + ", serviceAddress=" + serviceAddress + "]";
	}
}
