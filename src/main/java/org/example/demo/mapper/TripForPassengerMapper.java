package org.example.demo.mapper;

import org.example.demo.dto.response.TripForPassengerResponse;
import org.example.demo.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripForPassengerMapper {

    @Mapping(target = "driverId", source = "driver.id")
    TripForPassengerResponse toTripForPassengerResponse(Trip trip);

    List<TripForPassengerResponse> toTripForPassengerResponse(List<Trip> trip);
}
