package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drivers_reviews")
public class DriversReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "average_for_driver")
    private int averageForDriver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_user_id")
    private TripUser tripUser;
}
