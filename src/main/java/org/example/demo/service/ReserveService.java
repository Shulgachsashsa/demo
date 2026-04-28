package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.ReservePlaceRequest;
import org.example.demo.entity.Trip;
import org.example.demo.entity.TripUser;
import org.example.demo.entity.enums.State;
import org.example.demo.exceptions.NotFreeSeatsInTheCarException;
import org.example.demo.repository.TripRepository;
import org.example.demo.repository.TripUserRepository;
import org.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripUserRepository tripUserRepository;
    private final RatingOfPassengerService ratingOfPassengerService;

    public boolean reservePlace(ReservePlaceRequest request) {

        if (!checkFreePlaces(request.getTripId(), request.getQuantityPlaces()))
            throw new NotFreeSeatsInTheCarException("Not free seats!");

        minusPlacesFromTrip(request.getTripId(), request.getQuantityPlaces());

        TripUser tripUser = TripUser.builder()
                .state(State.RESERVED)
                .trip(tripRepository.getTripById(request.getTripId()))
                .user(userRepository.findUserById(request.getUserId()))
                .places(request.getQuantityPlaces())
                .build();

        tripUserRepository.save(tripUser);
        return true;
    }

    public boolean canceledFromReservation(Long id) {

        TripUser tripUser = tripUserRepository.getTripUsersById(id);
        tripUserRepository.updateStateById(tripUser.getUser().getId(), State.TRIP_IS_CANCELLED);
        backPlaces(tripUser);

        if (checkTimeForRating(tripUser))
            ratingOfPassengerService.addRatingForPassengerById(tripUser.getUser().getId(), 1);

        return true;
    }

    public void backPlaces(TripUser tripUser) {
        int actualityPlaces = tripRepository.getTripById
                (tripUser.getTrip().getId()).getNumberOfAvailableSeats() +
                tripUser.getPlaces();

        tripRepository.updateTrips(actualityPlaces, tripUser.getTrip().getId());
    }

    public boolean checkFreePlaces(Long tripId, int places) {
        return tripRepository.getTripById(tripId).getNumberOfAvailableSeats() >= places;
    }

    public void minusPlacesFromTrip(Long tripId, int places) {
        Trip trip = tripRepository.getTripById(tripId);
        tripRepository.updateTrips(trip.getNumberOfAvailableSeats() - places, tripId);
    }

    public boolean checkTimeForRating(TripUser tripUser) {
        Trip trip = tripRepository.getTripById(tripUser.getTrip().getId());
        Date now = new Date();
        long timeUntilDeparture = trip.getDateOfDeparture().getTime() - now.getTime();
        long twoHoursInMillis = 2 * 60 * 60 * 1000;
        return timeUntilDeparture > 0 && timeUntilDeparture <= twoHoursInMillis;
    }

}
