package org.example.demo.repository;

import org.example.demo.entity.RatingOfDriver;
import org.example.demo.entity.RatingOfPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingOfDriverRepository extends JpaRepository<RatingOfDriver, Long> {

    @Query("""
           SELECT r.averageGrade, r.totalCounterTrip
           FROM RatingOfDriver r
           WHERE r.driver.id = :id
           """)
    RatingOfDriver getPartRatingOfDriverById(@Param("id") Long id);

    @Modifying
    @Query("""
           UPDATE RatingOfDriver r
           SET r.totalCounterTrip = r.totalCounterTrip + 1
           WHERE r.driver.id = :id
           """)
    void incrementTotalCounterTrip(@Param("id") Long id);

    @Query("""
           SELECT r
           FROM RatingOfDriver r
           WHERE r.driver.id = :id
           """)
    RatingOfDriver getRatingOfDriverByDriverId(@Param("id") Long id);

    @Modifying
    @Query("""
           UPDATE RatingOfDriver r
           SET r.totalGrades = :totalGrades,
               r.averageGrade = :averageGrade,
               r.totalGradeCounter = :totalGradeCounter
           WHERE r.driver.id = :id
           """)
    void updateRatingByDriverId(@Param("totalGradeCounter") int totalGradeCounter,
                          @Param("totalGrades") int totalGrades,
                          @Param("averageGrade") double averageGrade,
                          @Param("id") Long id);
}
