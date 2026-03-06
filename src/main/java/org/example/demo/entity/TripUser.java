package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.demo.entity.enums.State;

@Data
@Entity
@Table(name = "trip_user")
public class TripUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToOne(mappedBy = "tripUser")
    private PassengersReviews passengersReviews;

    @OneToOne(mappedBy = "tripUser")
    private DriversReviews driversReviews;

}
