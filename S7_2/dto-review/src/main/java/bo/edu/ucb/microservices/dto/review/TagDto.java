package bo.edu.ucb.microservices.dto.review;

public class TagDto {
	private String name;

	
	public TagDto() {
		super();
	}

	public TagDto(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
