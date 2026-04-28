package org.example.demo.repository;

import org.example.demo.entity.PassengersReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengersReviewsRepository extends JpaRepository<PassengersReviews, Long> {

}
