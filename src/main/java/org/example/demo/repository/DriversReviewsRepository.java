package org.example.demo.repository;

import org.example.demo.entity.DriversReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversReviewsRepository extends JpaRepository<DriversReviews, Long> {

}
