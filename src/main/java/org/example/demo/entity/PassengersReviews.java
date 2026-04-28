package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "passengers_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengersReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "average_for_user")
    private int averageForUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_user_id")
    private TripUser tripUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
