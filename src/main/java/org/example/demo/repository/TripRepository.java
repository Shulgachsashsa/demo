package org.example.demo.repository;

import org.example.demo.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Modifying
    @Query("UPDATE Trip SET actuality = false WHERE dateOfDeparture < CURRENT_TIMESTAMP AND actuality = true")
    int expiredOldTrips();

}
