package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.Driver;
import org.example.demo.entity.RatingOfDriver;
import org.example.demo.repository.RatingOfDriverRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingOfDriverService {

    private final RatingOfDriverRepository ratingOfDriverRepository;

    public void createRatingForDriver(Driver driver) {
        RatingOfDriver rating = RatingOfDriver.builder()
                .totalCounterTrip(0)
                .averageGrade(0)
                .totalGradeCounter(0)
                .totalGrades(0)
                .driver(driver)
                .build();

        ratingOfDriverRepository.save(rating);
    }

}
