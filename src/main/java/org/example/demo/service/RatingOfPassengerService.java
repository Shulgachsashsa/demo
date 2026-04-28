package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.RatingOfPassenger;
import org.example.demo.entity.User;
import org.example.demo.repository.RatingOfPassengerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
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

    public void addRatingForPassengerById(Long id, int grade) {
        RatingOfPassenger rating = ratingOfPassengerRepository.getRatingOfPassengersByUserId(id);
        int totalGradeCounter = rating.getTotalGradeCounter() + 1;
        int totalGrades = rating.getTotalGrades() + grade;
        double averageGrade = (double) totalGrades / totalGradeCounter;
        ratingOfPassengerRepository.updateRatingById(totalGradeCounter, totalGrades, averageGrade, id);
    }

    public void addOneFromTotalCounterTrip(Long id) {
        ratingOfPassengerRepository.incrementTotalCounterTrip(id);
    }

}
