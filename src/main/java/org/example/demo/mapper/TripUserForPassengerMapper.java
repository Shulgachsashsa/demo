package org.example.demo.mapper;

import org.example.demo.dto.response.TripUserForPassengerResponse;
import org.example.demo.entity.TripUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TripForPassengerMapper.class)
public interface TripUserForPassengerMapper {
    TripUserForPassengerResponse toTripUserForPassengerMapper(TripUser tripUser);
    List<TripUserForPassengerResponse> toTripUserForPassengerMapper(List<TripUser> tripUser);
}
