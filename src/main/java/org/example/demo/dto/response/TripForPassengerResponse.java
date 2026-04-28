package org.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripForPassengerResponse {
    private Long id;
    private Date dateOfDeparture;
    private Date timeOfArrival;
    private String cityFrom;
    private String cityTo;
    private Double price;
    private Long driverId;
}
