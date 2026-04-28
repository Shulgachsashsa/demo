package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.entity.enums.State;

@Data
@Entity
@Table(name = "trip_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "places")
    private int places;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "tripUser", cascade = CascadeType.ALL)
    private PassengersReviews passengersReviews;

    @OneToOne(mappedBy = "tripUser", cascade =  CascadeType.ALL)
    private DriversReviews driversReviews;

}
