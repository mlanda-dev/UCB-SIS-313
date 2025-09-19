package bo.edu.ucb.microservices.core.msreview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//Imagen que acompaña a una reseña
@Entity
public class ReviewImage {
	@Id
	@GeneratedValue
	private Long id;

	private String url;

	// Relación inversa a Review (ManyToOne porque una imagen pertenece a una sola
	// reseña)
	@ManyToOne
	@JoinColumn(name = "review_id")
	private Review review;

	public ReviewImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewImage(Long id, String url, Review review) {
		super();
		this.id = id;
		this.url = url;
		this.review = review;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}
