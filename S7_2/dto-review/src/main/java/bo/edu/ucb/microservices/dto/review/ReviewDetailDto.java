package bo.edu.ucb.microservices.dto.review;

import java.time.LocalDateTime;

public class ReviewDetailDto {
	private LocalDateTime visitDate;
	private String extraNotes;

	public ReviewDetailDto() {

	}

	public ReviewDetailDto(LocalDateTime visitDate, String extraNotes) {
		super();
		this.visitDate = visitDate;
		this.extraNotes = extraNotes;
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

}
