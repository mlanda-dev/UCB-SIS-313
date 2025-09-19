package bo.edu.ucb.microservices.core.msreview.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import bo.edu.ucb.microservices.core.msreview.model.Review;
import bo.edu.ucb.microservices.core.msreview.model.ReviewDetail;
import bo.edu.ucb.microservices.core.msreview.model.ReviewImage;
import bo.edu.ucb.microservices.core.msreview.model.Tag;
import bo.edu.ucb.microservices.dto.review.ReviewDetailDto;
import bo.edu.ucb.microservices.dto.review.ReviewDto;
import bo.edu.ucb.microservices.dto.review.ReviewImageDto;
import bo.edu.ucb.microservices.dto.review.TagDto;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	@Mappings({ @Mapping(target = "serviceAddress", ignore = true) })
	ReviewDto entityToDto(Review entity);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "version", ignore = true) })
	Review dtoToEntity(ReviewDto dto);

	List<ReviewDto> entityListToDtoList(List<Review> entity);

	List<Review> dtoListToEntityList(List<ReviewDto> dto);
	
	ReviewDetailDto toDto(ReviewDetail entity);
	
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "review", ignore = true)})
	ReviewDetail toEntity(ReviewDetailDto dto);
	
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "review", ignore = true)})
	ReviewImage toEntity(ReviewImageDto dto);
	
	ReviewImageDto toDto(ReviewImage entity);
	
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "reviews", ignore = true)})
	Tag toEntity(TagDto dto);
	
	TagDto toDto(Tag entity);

}
