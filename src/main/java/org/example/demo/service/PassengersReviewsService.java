package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.example.demo.dto.request.ReviewForDriverRequest;
import org.example.demo.dto.request.ReviewsForUserRequest;
import org.example.demo.entity.DriversReviews;
import org.example.demo.entity.PassengersReviews;
import org.example.demo.entity.Trip;
import org.example.demo.entity.TripUser;
import org.example.demo.exceptions.TimeMoreThanTripException;
import org.example.demo.mapper.DriversReviewsMapper;
import org.example.demo.repository.DriversReviewsRepository;
import org.example.demo.repository.PassengersReviewsRepository;
import org.example.demo.repository.TripRepository;
import org.example.demo.repository.TripUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Data
@Transactional
public class PassengersReviewsService {

    private final PassengersReviewsRepository passengersReviewsRepository;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final RatingOfPassengerService ratingOfPassengerService;

    public void setGradeForUser(ReviewsForUserRequest request) {
        TripUser tripUser = tripUserRepository.getTripUsersById(request.getTripUserId());
        Trip trip = tripRepository.getTripById(tripUser.getTrip().getId());
        Date date = trip.getTimeOfArrival();
        LocalDateTime tripDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if (tripDateTime.isBefore(LocalDateTime.now()))
            throw new TimeMoreThanTripException("Now time more than trip");
        else {
            ratingOfPassengerService.addRatingForPassengerById(tripUser.getUser().getId(), request.getAverageForUser());
            PassengersReviews passengersReviews = PassengersReviews.builder()
                    .averageForUser(request.getAverageForUser())
                    .trip(trip)
                    .tripUser(tripUserRepository.getTripUsersById(request.getTripUserId()))
                    .build();
            passengersReviewsRepository.save(passengersReviews);
        }
    }
}
