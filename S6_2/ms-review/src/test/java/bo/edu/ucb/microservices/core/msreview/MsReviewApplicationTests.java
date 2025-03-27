package bo.edu.ucb.microservices.core.msreview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import bo.edu.ucb.microservices.core.msreview.model.Review;
import bo.edu.ucb.microservices.core.msreview.repository.ReviewRepository;
import bo.edu.ucb.microservices.dto.review.ReviewDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MsReviewApplicationTests extends PostgreSqlTestBase {

	@Autowired
	private WebTestClient client;

	@Autowired
	  private ReviewRepository repository;

	  @BeforeEach
	  void setupDb() {
	    repository.deleteAll();
	  }

	  @Test
	  void getReviewsByProductId() {

	    int productId = 1;

	    assertEquals(0, repository.findByProductId(productId).size());

	    postAndVerifyReview(productId, 1, HttpStatus.OK);
	    postAndVerifyReview(productId, 2, HttpStatus.OK);
	    postAndVerifyReview(productId, 3, HttpStatus.OK);

	    assertEquals(3, repository.findByProductId(productId).size());

	    getAndVerifyReviewsByProductId(productId, HttpStatus.OK)
	      .jsonPath("$.length()").isEqualTo(3)
	      .jsonPath("$[2].productId").isEqualTo(productId)
	      .jsonPath("$[2].reviewId").isEqualTo(3);
	  }

	  @Test
	  void duplicateError() {

	    int productId = 1;
	    int reviewId = 1;

	    assertEquals(0, repository.count());

	    postAndVerifyReview(productId, reviewId, HttpStatus.OK)
	      .jsonPath("$.productId").isEqualTo(productId)
	      .jsonPath("$.reviewId").isEqualTo(reviewId);

	    assertEquals(1, repository.count());

	    postAndVerifyReview(productId, reviewId, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/v1/review");

	    assertEquals(1, repository.count());
	  }

	  @Test
	  void deleteReviews() {

	    int productId = 1;
	    int reviewId = 1;

	    postAndVerifyReview(productId, reviewId, HttpStatus.OK);
	    assertEquals(1, repository.findByProductId(productId).size());

	    deleteAndVerifyReviewsByProductId(productId, HttpStatus.OK);
	    assertEquals(0, repository.findByProductId(productId).size());

	    deleteAndVerifyReviewsByProductId(productId, HttpStatus.OK);
	  }

	  @Test
	  void getReviewsMissingParameter() {

	    getAndVerifyReviewsByProductId("", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/v1/review");
	  }

	  @Test
	  void getReviewsInvalidParameter() {

	    getAndVerifyReviewsByProductId("?productId=no-integer", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/v1/review");
	  }

	  @Test
	  void getReviewsNotFound() {

	    getAndVerifyReviewsByProductId("?productId=213", HttpStatus.OK)
	      .jsonPath("$.length()").isEqualTo(0);
	  }

	  @Test
	  void getReviewsInvalidParameterNegativeValue() {

	    int productIdInvalid = -1;

	    getAndVerifyReviewsByProductId("?productId=" + productIdInvalid, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/v1/review");
	  }

	  private WebTestClient.BodyContentSpec getAndVerifyReviewsByProductId(int productId, HttpStatus expectedStatus) {
	    return getAndVerifyReviewsByProductId("?productId=" + productId, expectedStatus);
	  }

	  private WebTestClient.BodyContentSpec getAndVerifyReviewsByProductId(String productIdQuery, HttpStatus expectedStatus) {
	    return client.get()
	      .uri("/v1/review" + productIdQuery)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

	  private WebTestClient.BodyContentSpec postAndVerifyReview(int productId, int reviewId, HttpStatus expectedStatus) {
	    ReviewDto review = new ReviewDto(productId, reviewId, "Author " + reviewId, "Subject " + reviewId, "Content " + reviewId, "SA");
	    return client.post()
	      .uri("/v1/review")
	      .body(just(review), Review.class)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

	  private WebTestClient.BodyContentSpec deleteAndVerifyReviewsByProductId(int productId, HttpStatus expectedStatus) {
	    return client.delete()
	      .uri("/v1/review?productId=" + productId)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectBody();
	  }
}
