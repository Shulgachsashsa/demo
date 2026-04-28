package org.example.demo.mapper;

import org.example.demo.dto.response.DriversReviewsResponse;
import org.example.demo.entity.DriversReviews;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriversReviewsMapper {
    DriversReviewsResponse toDriversReviewsResponse(DriversReviews driversReviews);
    List<DriversReviewsResponse> toDriversReviewsResponse(List<DriversReviews> driversReviews);
}
