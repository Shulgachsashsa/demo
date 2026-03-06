package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_available_seats")
    private int numberOfAvailableSeats;

    @Column(name = "date_of_departure")
    private Date dateOfDeparture;

    @Column(name = "time_of_arrival")
    private Date timeOfArrival;

    @Column(name = "location_of_coordinates")
    private String locationOfCoordinates;

    @Column(name = "price")
    private Double price;

    @Column(name = "actuality")
    private boolean actuality;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "trip")
    private List<TripUser> tripUsers = new ArrayList<>();
}
