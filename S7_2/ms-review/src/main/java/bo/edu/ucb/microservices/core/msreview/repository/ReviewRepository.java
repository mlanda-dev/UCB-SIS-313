package bo.edu.ucb.microservices.core.msreview.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import bo.edu.ucb.microservices.core.msreview.model.Review;


public interface ReviewRepository extends JpaRepository<Review, Integer> {

	  @Transactional(readOnly = true)
	  List<Review> findByProductId(int productId);
	  
	  List<Review> findBySubjectLike(String subject, Pageable page);
	  
	  @Query("SELECT r FROM Review r JOIN r.detail rd WHERE r.reviewId = :paramReviewId")
	  Review getRegistryByReviewId(@Param("paramReviewId") int reviewId);

	  @Query(value="SELECT r.* FROM reviews r JOIN review_detail d ON r.detail_id=d.id WHERE r.review_id = :paramReviewId", 
			  nativeQuery = true)
	  Review getRegistryByReviewId2(@Param("paramReviewId") int reviewId);
}
	