package org.example.demo.repository;

import org.springframework.data.repository.query.Param;
import org.example.demo.entity.RatingOfPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingOfPassengerRepository extends JpaRepository<RatingOfPassenger, Long> {

    @Modifying
    @Query("""
           UPDATE RatingOfPassenger r
           SET r.totalCounterTrip = r.totalCounterTrip + 1
           WHERE r.user.id = :id
           """)
    void incrementTotalCounterTrip(@Param("id") Long id);

    @Query("""
           SELECT r
           FROM RatingOfPassenger r
           WHERE r.user.id = :id
           """)
    RatingOfPassenger getRatingOfPassengersByUserId(@Param("id") Long id);

    @Modifying
    @Query("""
           UPDATE RatingOfPassenger r
           SET r.totalGrades = :totalGrades,
               r.averageGrade = :averageGrade,
               r.totalGradeCounter = :totalGradeCounter
           WHERE r.user.id = :id
           """)
    void updateRatingById(@Param("totalGradeCounter") int totalGradeCounter,
                          @Param("totalGrades") int totalGrades,
                          @Param("averageGrade") double averageGrade,
                          @Param("id") Long id);
}
