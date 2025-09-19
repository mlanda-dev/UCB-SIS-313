package bo.edu.ucb.microservices.core.ms_recommendation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import bo.edu.ucb.microservices.core.ms_recommendation.mapper.RecommendationMapper;
import bo.edu.ucb.microservices.core.ms_recommendation.model.Recommendation;
import bo.edu.ucb.microservices.core.ms_recommendation.repository.RecommendationRepository;
import bo.edu.ucb.microservices.dto.recommendation.RecommendationDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;

@Service
public class RecommendationService {

	private static Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
	private final ServiceUtil serviceUtil;
	private final RecommendationRepository repository;
	private final RecommendationMapper mapper;
	public RecommendationService(ServiceUtil serviceUtil, RecommendationRepository repository,
			RecommendationMapper mapper) {
		super();
		this.serviceUtil = serviceUtil;
		this.repository = repository;
		this.mapper = mapper;
	}
	

	  public RecommendationDto createRecommendation(RecommendationDto body) {
	    try {
	      Recommendation entity = mapper.dtoToEntity(body);
	      Recommendation newEntity = repository.save(entity);

	      LOGGER.debug("createRecommendation: se creó la recomendación: {}/{}", body.getProductId(), body.getRecommendationId());
	      return mapper.entityToDto(newEntity);

	    } catch (DuplicateKeyException dke) {
	      throw new InvalidInputException("Llave duplicada, Product Id: " + body.getProductId() + ", Recommendation Id:" + body.getRecommendationId());
	    }
	  }


	  public List<RecommendationDto> getRecommendations(int productId) {

	    if (productId < 1) {
	      throw new InvalidInputException("productId inválido: " + productId);
	    }
	    
	    List<Recommendation> entityList = repository.findByProductId(productId);
	    List<RecommendationDto> list = mapper.entityListToDtoList(entityList);
	    list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

	    LOGGER.debug("getRecommendations: tamaño del listado: {}", list.size());

	    return list;
	  }


	  public void deleteRecommendations(int productId) {
	    LOGGER.debug("deleteRecommendations: eliminando recomendaciones para el producto con productId: {}", productId);
	    repository.deleteAll(repository.findByProductId(productId));
	  }
}
