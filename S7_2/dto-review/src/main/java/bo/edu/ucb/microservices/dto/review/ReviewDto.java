package bo.edu.ucb.microservices.dto.review;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReviewDto {

	private int productId;
	private int reviewId;
	private String author;
	private String subject;
	private String content;
	private String serviceAddress;

	private List<ReviewImageDto> images = new ArrayList<>();
	private ReviewDetailDto detail;
	private Set<TagDto> tags = new HashSet<>();

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

	public void setProductId(int productId) {
		this.productId = productId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public List<ReviewImageDto> getImages() {
		return images;
	}

	public void setImages(List<ReviewImageDto> images) {
		this.images = images;
	}

	public ReviewDetailDto getDetail() {
		return detail;
	}

	public void setDetail(ReviewDetailDto detail) {
		this.detail = detail;
	}

	public Set<TagDto> getTags() {
		return tags;
	}

	public void setTags(Set<TagDto> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "ReviewDto [productId=" + productId + ", reviewId=" + reviewId + ", author=" + author
				+ ", subject=" + subject + ", content=" + content + ", serviceAddress="
				+ serviceAddress + "]";
	}
}
