package bo.edu.ucb.microservices.core.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import bo.edu.ucb.microservices.core.product.mapper.ProductMapper;
import bo.edu.ucb.microservices.core.product.model.Product;
import bo.edu.ucb.microservices.core.product.repository.ProductRepository;
import bo.edu.ucb.microservices.dto.product.ProductDto;
import bo.edu.ucb.microservices.util.exceptions.InvalidInputException;
import bo.edu.ucb.microservices.util.exceptions.NotFoundException;
import bo.edu.ucb.microservices.util.http.ServiceUtil;

@Service
public class ProductService {

	private static final Logger LOGGER=LoggerFactory.getLogger(ProductService.class);
	private final ServiceUtil serviceUtil;
	private final ProductRepository repository;
	private final ProductMapper mapper;

	@Autowired
	public ProductService(ServiceUtil serviceUtil, ProductRepository repository, ProductMapper mapper) {
		super();
		this.serviceUtil = serviceUtil;
		this.repository = repository;
		this.mapper = mapper;
	}

	public ProductDto createProduct(ProductDto productDto) {
		try {
		      Product entity = mapper.dtoToEntity(productDto);
		      Product newEntity = repository.save(entity);

		      LOGGER.debug("createProduct: entidad creada para productId: {}", productDto.getProductId());
		      return mapper.entityToDto(newEntity);

		    } catch (DuplicateKeyException dke) {
		      throw new InvalidInputException("Llave duplicada, Product Id: " + productDto.getProductId());
		    }
	}
	
	public ProductDto getProduct(int productId) {
		if (productId < 1) {
		      throw new InvalidInputException("productId inválido: " + productId);
		    }

		    Product entity = repository.findByProductId(productId)
		      .orElseThrow(() -> new NotFoundException("No se encontró producto para productId: " + productId));

		    ProductDto response = mapper.entityToDto(entity);
		    response.setServiceAddress(serviceUtil.getServiceAddress());

		    LOGGER.debug("getProduct: productId encontrado: {}", response.getProductId());

		    return response;
	}
	
	public void deleteProduct(int productId) {
	    LOGGER.debug("deleteProduct: eliminando producto con productId: {}", productId);
	    repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
	}
}
