package org.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservePlaceRequest {
    private Long tripId;
    private Long userId;
    private int quantityPlaces;
}
