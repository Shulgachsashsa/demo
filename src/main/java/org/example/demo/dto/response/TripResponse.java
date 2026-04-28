package org.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripResponse {
    private Long id;
    private int numberOfAvailableSeats;
    private Date dateOfDeparture;
    private Date timeOfArrival;
    private String locationOfCoordinates;
    private String cityFrom;
    private String cityTo;
    private Double price;
    private boolean actuality;
    private Long driverId;
    private List<TripUserResponse> tripUsers = new ArrayList<>();
}
