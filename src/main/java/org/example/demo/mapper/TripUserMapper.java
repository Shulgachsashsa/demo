package org.example.demo.mapper;

import org.example.demo.dto.response.TripUserResponse;
import org.example.demo.entity.TripUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TripUserMapper {
    TripUserResponse toTripUserResponse(TripUser tripUser);
    List<TripUserResponse> toListTripUserResponse(List<TripUser> tripUsers);
}
