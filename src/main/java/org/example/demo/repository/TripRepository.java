package org.example.demo.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.demo.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Modifying
    @Query("UPDATE Trip SET actuality = false WHERE dateOfDeparture < CURRENT_TIMESTAMP AND actuality = true")
    int expiredOldTrips();

    @Query("SELECT t FROM Trip t WHERE t.id = :id")
    Trip getTripById(@Param("id") Long id);

    @Query("SELECT t, rto.totalCounterTrip, rto.averageGrade FROM Trip t " +
            "LEFT JOIN FETCH t.driver " +
            "LEFT JOIN RatingOfDriver rto ON rto.driver.id = t.driver.id " +
            "WHERE t.cityFrom = :cityFrom " +
            "AND t.cityTo = :cityTo " +
            "AND t.actuality = true " +
            "AND t.dateOfDeparture >= :startOfDay " +
            "AND t.dateOfDeparture < :nextDay " +
            "AND t.numberOfAvailableSeats > 0")
    List<Object[]> findTripsByRoutesWithDriver(@Param("cityFrom") String cityFrom,
                                              @Param("cityTo") String cityTo,
                                              @Param("startOfDay") Date startOfDay,
                                              @Param("nextDay") Date nextDay);

    @Modifying
    @Query(value = "UPDATE Trip SET number_of_available_seats = :places WHERE id = :id", nativeQuery = true)
    void updateTrips(@Param("places") int places, @Param("id") Long id);

    @Query("""
          SELECT t
          FROM Trip t
          WHERE t.driver.id = :driverId
          """)
    List<Trip> getTripsByDriverId(@Param("driverId") Long driverId);

    @Query("""
           SELECT t
           FROM Trip t
           WHERE t.driver.id = :driverId
           AND t.actuality = true
           """)
    List<Trip> getActuallyTripsByDriverId(@Param("driverId") Long driverId);
}
