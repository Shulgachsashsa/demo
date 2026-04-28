package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.ReviewForDriverRequest;
import org.example.demo.dto.request.ReviewsForUserRequest;
import org.example.demo.entity.PassengersReviews;
import org.example.demo.service.PassengersReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passengers-reviews")
@Tag(name = "Work with drivers review")
@RequiredArgsConstructor
public class PassengersReviewsController {

    private final PassengersReviewsService passengersReviewsService;

    @PostMapping("/grade")
    public ResponseEntity<?> gradePassengers(@RequestBody ReviewsForUserRequest request) {
        passengersReviewsService.setGradeForUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
