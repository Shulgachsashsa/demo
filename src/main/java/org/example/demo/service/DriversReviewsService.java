package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.ReviewForDriverRequest;
import org.example.demo.entity.DriversReviews;
import org.example.demo.entity.Trip;
import org.example.demo.exceptions.TimeMoreThanTripException;
import org.example.demo.mapper.DriversReviewsMapper;
import org.example.demo.repository.DriversReviewsRepository;
import org.example.demo.repository.TripRepository;
import org.example.demo.repository.TripUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Data
@Transactional
public class DriversReviewsService {

    private final DriversReviewsRepository driversReviewsRepository;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final RatingOfDriverService ratingOfDriverService;
    private final DriversReviewsMapper driversReviewsMapper;

    public void setGradeForDriver(ReviewForDriverRequest request) {
        Trip trip = tripRepository.getTripById(
                tripUserRepository.getTripUsersById(request.getTripUserId()).getTrip().getId());

        Date date = trip.getTimeOfArrival();
        LocalDateTime tripDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if (tripDateTime.isBefore(LocalDateTime.now()))
            throw new TimeMoreThanTripException("Now time more than trip");
        else {
            ratingOfDriverService.addRatingForDriverById(trip.getDriver().getId(), request.getAverageForDriver());
            DriversReviews driversReviews = DriversReviews.builder()
                    .averageForDriver(request.getAverageForDriver())
                    .trip(trip)
                    .tripUser(tripUserRepository.getTripUsersById(request.getTripUserId()))
                    .build();
            driversReviewsRepository.save(driversReviews);
        }
    }
}
