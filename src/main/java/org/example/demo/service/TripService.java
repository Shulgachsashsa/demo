package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.request.CreateTripRequest;
import org.example.demo.dto.request.FindTripsByRoutesRequest;
import org.example.demo.dto.request.SetStatePassengerRequest;
import org.example.demo.dto.response.FindTripsResponse;
import org.example.demo.dto.response.TripResponse;
import org.example.demo.dto.response.TripWithImagesResponse;
import org.example.demo.entity.Driver;
import org.example.demo.entity.Trip;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.State;
import org.example.demo.exceptions.DriverNotExistsException;
import org.example.demo.exceptions.NotCorrectStateException;
import org.example.demo.exceptions.UserNotFoundException;
import org.example.demo.mapper.TripMapper;
import org.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;
    private final DriverRepository driverRepository;
    private final MinioRepository minioRepository;
    private final TripUserRepository tripUserRepository;
    private final RatingOfPassengerService ratingOfPassengerService;
    private final TripMapper tripMapper;

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

    public FindTripsResponse getTripsByRoutes(FindTripsByRoutesRequest request) {
        Date dateOfDeparture = request.getDateOfDeparture();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfDeparture);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date nextDay = cal.getTime();

        log.info("Searching from {} to {} for dates >= {} and < {}",
                request.getCityFrom(), request.getCityTo(), startOfDay, nextDay);

        List<Object[]> results = tripRepository.findTripsByRoutesWithDriver(
                request.getCityFrom(),
                request.getCityTo(),
                startOfDay,
                nextDay
        );

        log.info("Found {} trips", results.size());

        List<TripWithImagesResponse> responseTrips = results.stream()
                .map(result -> {
                    Trip trip = (Trip) result[0];
                    int totalCounterTrip = result[1] != null ? ((Number) result[1]).intValue() : 0;
                    double averageGrade = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;

                    return convertToTripWithImages(trip, totalCounterTrip, averageGrade);
                })
                .collect(Collectors.toList());

        FindTripsResponse response = new FindTripsResponse();
        response.setTrips(responseTrips);
        return response;
    }

    private TripWithImagesResponse convertToTripWithImages(Trip trip, int totalCounterTrip, double averageGrade) {
        TripWithImagesResponse dto = new TripWithImagesResponse();
        dto.setId(trip.getId());
        dto.setNumberOfAvailableSeats(trip.getNumberOfAvailableSeats());
        dto.setDateOfDeparture(trip.getDateOfDeparture());
        dto.setTimeOfArrival(trip.getTimeOfArrival());
        dto.setLocationOfCoordinates(trip.getLocationOfCoordinates());
        dto.setCityFrom(trip.getCityFrom());
        dto.setCityTo(trip.getCityTo());
        dto.setPrice(trip.getPrice());
        dto.setActuality(trip.isActuality());
        dto.setTotalCounterTrip(totalCounterTrip);
        dto.setAverageGrade(averageGrade);

        if (trip.getDriver() != null) {
            dto.setDriverId(trip.getDriver().getId());

            List<String> imageNames = minioRepository.findImageNamesByDriverId(trip.getDriver().getId());

            List<String> imageUrls = imageNames.stream()
                    .map(name -> "http://localhost:9001/browser/carsbucket/" + name)
                    .collect(Collectors.toList());

            dto.setCarImageUrls(imageUrls);
        } else {
            dto.setCarImageUrls(new ArrayList<>());
        }
        return dto;
    }

    public void updateStateOfPassenger(SetStatePassengerRequest request) {
        if (request.getState() == State.NO_SHOW)
            updateStateNoShow(request.getUserId());
        else if (request.getState() == State.LANDING_COMPLETED)
            updateStateLandingCompleted(request.getUserId());
        else
            throw new NotCorrectStateException("Not correct state: " + request.getState());
    }

    public void updateStateNoShow(Long id) {
        ratingOfPassengerService.addRatingForPassengerById(id, 1);
        tripUserRepository.updateStateById(id, State.NO_SHOW);
    }

    public void updateStateLandingCompleted(Long userId) {
        ratingOfPassengerService.addOneFromTotalCounterTrip(userId);
        tripUserRepository.updateStateById(userId, State.LANDING_COMPLETED);
    }

    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    public List<TripResponse> getTripByDriverId(Long id) {
        return tripMapper.toListTripResponse(tripRepository.getTripsByDriverId(id));
    }

    public List<TripResponse> getActuallyTripsByDriverId(Long id) {
        return tripMapper.toListTripResponse(tripRepository.getActuallyTripsByDriverId(id));
    }
}
