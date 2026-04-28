package org.example.demo.mapper;

import org.example.demo.dto.response.TripResponse;
import org.example.demo.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TripUserMapper.class)
public interface TripMapper {

    @Mapping(target = "driverId", source = "driver.id")
    TripResponse toTripResponse(Trip trip);

    List<TripResponse> toListTripResponse(List<Trip> trips);
}
