package bo.edu.ucb.microservices.core.msreview.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "reviews", indexes = {
		@Index(name = "reviews_unique_idx", unique = true, columnList = "productId,reviewId") })
public class Review {
	@Id
	@GeneratedValue
	private int id;

	@Version
	private int version;

	private String author;
	private int productId;
	private int reviewId;
	private String subject;
	private String content;
	
	/*
	 * ====================== Relaciones JPA extra ======================
	 */
	//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	//	@JoinColumn(name = "review_id", referencedColumnName = "id")
	// Un review puede tener muchas imágenes
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewImage> images = new ArrayList<>();

	// Un review puede tener un detalle adicional único, por ejemplo “ReviewDetail”
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detail_id", referencedColumnName = "id")
	private ReviewDetail detail;

	// Un review puede estar asociado a varios tags y viceversa
	@ManyToMany
	@JoinTable(name = "review_tag", 
				joinColumns = @JoinColumn(name = "review_id"),
				inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	public Review() {
	}

	public Review(int productId, int reviewId, String author, String subject, String content) {
		this.productId = productId;
		this.reviewId = reviewId;
		this.author = author;
		this.subject = subject;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public List<ReviewImage> getImages() {
		return images;
	}

	public void setImages(List<ReviewImage> images) {
		this.images = images;
	}

	public ReviewDetail getDetail() {
		return detail;
	}

	public void setDetail(ReviewDetail detail) {
		this.detail = detail;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

}
