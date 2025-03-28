package bo.edu.ucb.microservices.core.ms_recommendation.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import bo.edu.ucb.microservices.core.ms_recommendation.model.Recommendation;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
	  List<Recommendation> findByProductId(int productId);

}
