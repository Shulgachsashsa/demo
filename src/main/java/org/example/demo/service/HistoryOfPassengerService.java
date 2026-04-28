package org.example.demo.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.response.TripUserForPassengerResponse;
import org.example.demo.mapper.TripUserForPassengerMapper;
import org.example.demo.repository.TripUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class HistoryOfPassengerService {

    private final TripUserRepository tripUserRepository;
    private final TripUserForPassengerMapper tripUserForPassengerMapper;

    public List<TripUserForPassengerResponse> getAllTripsByUserId(Long id) {
       return tripUserForPassengerMapper.toTripUserForPassengerMapper(tripUserRepository.getTripUserByUserId(id));
    }

    public List<TripUserForPassengerResponse> getReservedTripsByUserId(Long id) {
        return tripUserForPassengerMapper.toTripUserForPassengerMapper(tripUserRepository.getReservedTripUserByUserId(id));
    }

}
