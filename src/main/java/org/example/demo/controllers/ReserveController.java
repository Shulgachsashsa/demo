package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.ReservePlaceRequest;
import org.example.demo.service.ReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "Reserving place")
public class ReserveController {

    private final ReserveService reserveService;

    @PostMapping("/place")
    public ResponseEntity<?> reserveTicket(@RequestBody @Valid ReservePlaceRequest request) {
        boolean reserved = reserveService.reservePlace(request);
        return reserved
                ? ResponseEntity.accepted().body(Map.of("message", "success"))
                : ResponseEntity.badRequest().body(Map.of("message", "bad request"));
    }

    @PatchMapping("/{tripUserId}/cancel")
    public ResponseEntity<?> cancelReservation(@RequestBody @Valid Long id) {
        boolean canceled = reserveService.canceledFromReservation(id);
        return canceled
                ? ResponseEntity.accepted().body(Map.of("message", "success"))
                : ResponseEntity.badRequest().body(Map.of("message", "bad request"));

    }

}
