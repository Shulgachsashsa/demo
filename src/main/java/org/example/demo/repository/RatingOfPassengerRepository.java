package org.example.demo.repository;

import org.example.demo.entity.RatingOfPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingOfPassengerRepository extends JpaRepository<RatingOfPassenger, Long> {

}
