package org.example.demo.repository;

import org.example.demo.entity.RatingOfDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingOfDriverRepository extends JpaRepository<RatingOfDriver, Long> {

}
