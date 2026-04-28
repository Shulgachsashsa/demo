package org.example.demo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FindTripsResponse {
    private List<TripWithImagesResponse> trips;
}
