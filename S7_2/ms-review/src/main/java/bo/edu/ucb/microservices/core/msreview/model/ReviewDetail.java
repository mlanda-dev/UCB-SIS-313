package bo.edu.ucb.microservices.core.msreview.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//Información extra 1 a 1
@Entity
public class ReviewDetail {
	@Id
	@GeneratedValue
	private Long id;

	private LocalDateTime visitDate;
	private String extraNotes;

	@OneToOne(mappedBy = "detail")
	private Review review;

	public ReviewDetail() {
		super();
	}

	public ReviewDetail(Long id, LocalDateTime visitDate, String extraNotes, Review review) {
		super();
		this.id = id;
		this.visitDate = visitDate;
		this.extraNotes = extraNotes;
		this.review = review;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDateTime visitDate) {
		this.visitDate = visitDate;
	}

	public String getExtraNotes() {
		return extraNotes;
	}

	public void setExtraNotes(String extraNotes) {
		this.extraNotes = extraNotes;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}
