package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.CreateTripRequest;
import org.example.demo.dto.request.SetStatePassengerRequest;
import org.example.demo.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trips")
@Tag(name = "Work with trip")
public class TripController {

    private final TripService tripService;

    @PostMapping("/creation")
    public ResponseEntity<?> createTrip(
            @RequestBody @Valid CreateTripRequest request) {

        tripService.createTrip(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Success",
                "success", "true"
        ));
    }

    @PatchMapping("/passenger-state")
    public ResponseEntity<?> setState(
            @RequestBody @Valid SetStatePassengerRequest request) {

        try {
            tripService.updateStateOfPassenger(request);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Success",
                    "success", "true"
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Error",
                    "success", "false"
            ));
        }
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long tripId) {
            tripService.deleteTrip(tripId);
            return new ResponseEntity<>(HttpStatus.OK);
    }

   /* @GetMapping("/{tripId}/passengers")
    public ResponseEntity<> getPassengersInTrip() {

    } */


}
