package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.response.TripResponse;
import org.example.demo.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/history/drivers")
@Tag(name = "History trips of driver")
@RequiredArgsConstructor
public class HistoryDriverController {

    private final TripService tripService;

    @GetMapping("/{driverId}/trips")
    public ResponseEntity<List<TripResponse>> getAllTrips(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.getTripByDriverId(id));
    }

    @GetMapping("/{driverId}/active-trips")
    public ResponseEntity<List<TripResponse>> getActuallyTrips(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.getActuallyTripsByDriverId(id));
    }

}
