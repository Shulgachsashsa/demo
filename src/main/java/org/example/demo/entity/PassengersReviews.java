package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "passengers_reviews")
public class PassengersReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "average_for_user")
    private int averageForUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_user_id")
    private TripUser tripUser;
}
