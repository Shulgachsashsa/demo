package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.response.TripUserForPassengerResponse;
import org.example.demo.service.HistoryOfPassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/history/passengers")
@Tag(name = "History trips of passengers")
@RequiredArgsConstructor
public class HistoryPassengerController {

    private final HistoryOfPassengerService historyOfPassengerService;

    @GetMapping("/{userId}/trips")
    public ResponseEntity<List<TripUserForPassengerResponse>> getAllTrips(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(historyOfPassengerService.getAllTripsByUserId(id));
    }

    @GetMapping("/{userId}/reserved-trips")
    public ResponseEntity<List<TripUserForPassengerResponse>> getReservedTrips(@PathVariable Long id) {
        System.out.println(id);
        return ResponseEntity.status(HttpStatus.OK).body(historyOfPassengerService.getReservedTripsByUserId(id));
    }
}
