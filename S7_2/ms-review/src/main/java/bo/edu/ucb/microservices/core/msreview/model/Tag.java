package bo.edu.ucb.microservices.core.msreview.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

//Etiquetas (tags) que pueden compartirse entre muchas reseñas
@Entity
public class Tag {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "tags")
	private Set<Review> reviews = new HashSet<>();

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tag(Long id, String name, Set<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
