package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.FindTripsByRoutesRequest;
import org.example.demo.dto.response.FindTripsResponse;
import org.example.demo.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
@Tag(name = "Work with schedule trips")
public class PassengerTripController {

    private final TripService tripService;

    @GetMapping("/trip")
    public ResponseEntity<FindTripsResponse> findTripsByRoutes(
            @RequestBody FindTripsByRoutesRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.getTripsByRoutes(request));
    }
}
