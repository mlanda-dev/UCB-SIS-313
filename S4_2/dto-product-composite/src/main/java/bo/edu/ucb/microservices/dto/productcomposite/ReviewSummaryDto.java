package bo.edu.ucb.microservices.dto.productcomposite;

public class ReviewSummaryDto {
	private int reviewId;
	private String author;
	private String subject;

	public ReviewSummaryDto() {

	}

	public ReviewSummaryDto(int reviewId, String author, String subject) {
		super();
		this.reviewId = reviewId;
		this.author = author;
		this.subject = subject;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "ReviewSummaryDto [reviewId=" + reviewId + ", author=" + author + ", subject=" + subject + "]";
	}
}
