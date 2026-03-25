package org.example.demo.repository;

import org.example.demo.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsDriverById(Long id);
    Optional<Driver> getDriverByUserId(Long id);

}
