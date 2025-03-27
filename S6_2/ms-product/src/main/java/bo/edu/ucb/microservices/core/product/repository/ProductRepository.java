package bo.edu.ucb.microservices.core.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import bo.edu.ucb.microservices.core.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	  Optional<Product> findByProductId(int productId);
	  
	  List<Product> findByWeightGreaterThanEqual(int weight);
	  
	  @Query("{'weight': {$gte: ?0, $lte: ?1} }")
	  List<Product> findProductsInWeightRange(int minWeight, int maxWeight);

}
