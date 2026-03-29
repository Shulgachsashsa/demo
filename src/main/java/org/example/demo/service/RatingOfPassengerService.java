package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.RatingOfPassenger;
import org.example.demo.entity.User;
import org.example.demo.repository.RatingOfPassengerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingOfPassengerService {

    private final RatingOfPassengerRepository ratingOfPassengerRepository;

    public void createRatingForNewPassenger(User user) {

        RatingOfPassenger rating = RatingOfPassenger.builder()
                .totalCounterTrip(0)
                .averageGrade(0)
                .totalGradeCounter(0)
                .totalGrades(0)
                .user(user)
                .build();

        ratingOfPassengerRepository.save(rating);
    }

}
