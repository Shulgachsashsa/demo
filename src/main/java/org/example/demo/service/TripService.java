package org.example.demo.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.request.CreateTripRequest;
import org.example.demo.entity.Driver;
import org.example.demo.entity.Trip;
import org.example.demo.entity.User;
import org.example.demo.exceptions.DriverNotExistsException;
import org.example.demo.exceptions.UserNotFoundException;
import org.example.demo.repository.DriverRepository;
import org.example.demo.repository.TripRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;
    private final DriverRepository driverRepository;

    public void createTrip(CreateTripRequest request) {

        User user = userService.getCurrentUser().orElseThrow(() ->
                new UserNotFoundException("User with actuality token not found"));

        Driver driver = driverRepository.getDriverByUserId(user.getId()).orElseThrow(() ->
                new DriverNotExistsException("Driver with actuality token not found"));

        Trip trip = Trip.builder()
                .dateOfDeparture(request.getDate_of_departure())
                .price(request.getPrice())
                .locationOfCoordinates(request.getLocationOfCoordinates())
                .timeOfArrival(request.getTimeOfArrival())
                .cityFrom(request.getCityFrom())
                .cityTo(request.getCityTo())
                .actuality(true)
                .driver(driver)
                .numberOfAvailableSeats(request.getNumberOfAvailableSeats())
                .build();

        tripRepository.save(trip);
    }

}
