package org.example.demo.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.demo.entity.TripUser;
import org.example.demo.entity.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, Long> {

    TripUser getTripUsersById(Long id);

    @Modifying
    @Query("""
           UPDATE TripUser t
           SET t.state = :state
           WHERE t.user.id = :id
           """)
    void updateStateById(@Param("id") Long id, @Param("state") State state);

    @Query("""
           SELECT t
           FROM TripUser t
           LEFT JOIN Trip tr
           ON t.trip.id = tr.id
           WHERE t.user.id = :id
           """)
    List<TripUser> getTripUserByUserId(@Param("id") Long id);

    @Query("""
           SELECT t
           FROM TripUser t
           LEFT JOIN FETCH Trip tr
           ON t.trip.id = tr.id
           WHERE t.user.id = :id
           AND t.state = 'RESERVED'
           """)
    List<TripUser> getReservedTripUserByUserId(@Param("id") Long id);
}
