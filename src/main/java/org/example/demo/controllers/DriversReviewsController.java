package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.ReviewForDriverRequest;
import org.example.demo.service.DriversReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/drivers-reviews")
@Tag(name = "Work with drivers review")
@RequiredArgsConstructor
public class DriversReviewsController {

    private final DriversReviewsService driversReviewsService;

    @PostMapping("/grade")
    public ResponseEntity<?> gradeDrivers(@RequestBody ReviewForDriverRequest request) {
        driversReviewsService.setGradeForDriver(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
