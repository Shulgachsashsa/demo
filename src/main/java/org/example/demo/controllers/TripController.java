package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.CreateTripRequest;
import org.example.demo.dto.response.CreateTripResponse;
import org.example.demo.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
@Tag(name = "Work with trip")
public class TripController {

    private final TripService tripService;

    @PostMapping("/create-trip")
    public ResponseEntity<?> createTrip(
            @RequestBody @Valid CreateTripRequest request) {

        tripService.createTrip(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Success",
                "success", "true"
        ));
    }

}
