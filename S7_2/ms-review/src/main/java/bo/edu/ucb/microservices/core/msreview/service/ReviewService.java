package bo.edu.ucb.microservices.core.msreview.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.edu.ucb.microservices.core.msreview.mapper.ReviewMapper;
import bo.edu.ucb.microservices.core.msreview.model.Review;
import bo.edu.ucb.microservices.core.msreview.repository.ReviewRepository;
import bo.edu.ucb.microservices.dto.review.ReviewDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;

@Service
public class ReviewService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

	private final ReviewRepository repository;

	private final ReviewMapper mapper;

	private final ServiceUtil serviceUtil;

	@Autowired
	public ReviewService(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.serviceUtil = serviceUtil;
	}
	
	  public ReviewDto createReview(ReviewDto body) {
	    try {
	      Review entity = mapper.dtoToEntity(body);
	      Review newEntity = repository.save(entity);

	      LOGGER.debug("createReview: created a review entity: {}/{}", body.getProductId(), body.getReviewId());
	      return mapper.entityToDto(newEntity);

	    } catch (DataIntegrityViolationException dive) {
	      throw new InvalidInputException("Llave duplicada, Product Id: " + body.getProductId() + ", Review Id:" + body.getReviewId());
	    }
	  }

	  public List<ReviewDto> getReviews(int productId) {

	    if (productId < 1) {
	      throw new InvalidInputException("productId inválido: " + productId);
	    }
	    
	    List<Review> entityList = repository.findByProductId(productId);
	    List<ReviewDto> list = mapper.entityListToDtoList(entityList);
	    list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

	    LOGGER.debug("getReviews: tamaño de respuesta: {}", list.size());

	    return list;
	  }

	  public void deleteReviews(int productId) {
		  LOGGER.debug("deleteReviews: eliminando reseñas para el producto con productId: {}", productId);
	    repository.deleteAll(repository.findByProductId(productId));
	  }

	  @Transactional(readOnly = true)
	  public List<ReviewDto> searchReviews(String subject){
		  Pageable pageable=PageRequest.of(0, 3, Sort.by("author").descending());
		  List<Review> entityList= repository.findBySubjectLike("%"+subject+"%", pageable);
		  List<ReviewDto> list = mapper.entityListToDtoList(entityList);
		  return list;
	  }
	  
	  @Transactional(readOnly = true)
	  public ReviewDto getReviewById(int id){
		  Review entity= repository.getRegistryByReviewId(id);
		  ReviewDto dto = mapper.entityToDto(entity);
		  return dto;
	  }
	  
	  @Transactional(readOnly = true)
	  public ReviewDto getReviewById2(int id){
		  Review entity= repository.getRegistryByReviewId2(id);
		  ReviewDto dto = mapper.entityToDto(entity);
		  return dto;
	  }
}
