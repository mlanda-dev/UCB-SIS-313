package bo.edu.ucb.microservices.core.ms_recommendation;

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

import bo.edu.ucb.microservices.core.ms_recommendation.model.Recommendation;
import bo.edu.ucb.microservices.core.ms_recommendation.repository.RecommendationRepository;
import bo.edu.ucb.microservices.dto.recommendation.RecommendationDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MsRecommendationApplicationTests extends MongoDbTestBase {

	  @Autowired
	  private WebTestClient client;

	  @Autowired
	  private RecommendationRepository repository;

	  @BeforeEach
	  void setupDb() {
	    repository.deleteAll();
	  }
	  
	  @Test
	  void getRecommendationsByProductId() {

	    int productId = 1;

	    postAndVerifyRecommendation(productId, 1, HttpStatus.OK);
	    postAndVerifyRecommendation(productId, 2, HttpStatus.OK);
	    postAndVerifyRecommendation(productId, 3, HttpStatus.OK);

	    assertEquals(3, repository.findByProductId(productId).size());

	    getAndVerifyRecommendationsByProductId(productId, HttpStatus.OK)
	      .jsonPath("$.length()").isEqualTo(3)
	      .jsonPath("$[2].productId").isEqualTo(productId)
	      .jsonPath("$[2].recommendationId").isEqualTo(3);
	  }

	  @Test
	  void duplicateError() {

	    int productId = 1;
	    int recommendationId = 1;

	    postAndVerifyRecommendation(productId, recommendationId, HttpStatus.OK)
	      .jsonPath("$.productId").isEqualTo(productId)
	      .jsonPath("$.recommendationId").isEqualTo(recommendationId);

	    assertEquals(1, repository.count());

	    postAndVerifyRecommendation(productId, recommendationId, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/v1/recommendation");

	    assertEquals(1, repository.count());
	  }

	  @Test
	  void deleteRecommendations() {

	    int productId = 1;
	    int recommendationId = 1;

	    postAndVerifyRecommendation(productId, recommendationId, HttpStatus.OK);
	    assertEquals(1, repository.findByProductId(productId).size());

	    deleteAndVerifyRecommendationsByProductId(productId, HttpStatus.OK);
	    assertEquals(0, repository.findByProductId(productId).size());

	    deleteAndVerifyRecommendationsByProductId(productId, HttpStatus.OK);
	  }

	  @Test
	  void getRecommendationsMissingParameter() {

	    getAndVerifyRecommendationsByProductId("", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/v1/recommendation");
	  }

	  @Test
	  void getRecommendationsInvalidParameter() {

	    getAndVerifyRecommendationsByProductId("?productId=no-integer", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/v1/recommendation");
	  }

	  @Test
	  void getRecommendationsNotFound() {

	    getAndVerifyRecommendationsByProductId("?productId=113", HttpStatus.OK)
	      .jsonPath("$.length()").isEqualTo(0);
	  }

	  @Test
	  void getRecommendationsInvalidParameterNegativeValue() {

	    int productIdInvalid = -1;

	    getAndVerifyRecommendationsByProductId("?productId=" + productIdInvalid, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/v1/recommendation");
	  }

	  private WebTestClient.BodyContentSpec getAndVerifyRecommendationsByProductId(int productId, HttpStatus expectedStatus) {
	    return getAndVerifyRecommendationsByProductId("?productId=" + productId, expectedStatus);
	  }

	  private WebTestClient.BodyContentSpec getAndVerifyRecommendationsByProductId(String productIdQuery, HttpStatus expectedStatus) {
	    return client.get()
	      .uri("/v1/recommendation" + productIdQuery)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

	  private WebTestClient.BodyContentSpec postAndVerifyRecommendation(int productId, int recommendationId, HttpStatus expectedStatus) {
	    RecommendationDto recommendation = new RecommendationDto(productId, recommendationId, "Author " + recommendationId, recommendationId, "Content " + recommendationId, "SA");
	    return client.post()
	      .uri("/v1/recommendation")
	      .body(just(recommendation), Recommendation.class)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

	  private WebTestClient.BodyContentSpec deleteAndVerifyRecommendationsByProductId(int productId, HttpStatus expectedStatus) {
	    return client.delete()
	      .uri("/v1/recommendation?productId=" + productId)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectBody();
	  }
}
