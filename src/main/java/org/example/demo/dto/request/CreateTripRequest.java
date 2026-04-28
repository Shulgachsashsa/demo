package org.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create trip")
public class CreateTripRequest {
    private String locationOfCoordinates;
    private Date date_of_departure;
    private Date timeOfArrival;
    private int numberOfAvailableSeats;
    private double price;
    private String cityFrom;
    private String cityTo;
}
