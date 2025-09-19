package bo.edu.ucb.microservices.dto.review;

public class ReviewImageDto {
	private String url;

	public ReviewImageDto() {
	}

	public ReviewImageDto(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
