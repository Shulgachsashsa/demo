package org.example.demo.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripWithImagesResponse {
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
    private List<String> carImageUrls;
    private double averageGrade;
    private int totalCounterTrip;
}
