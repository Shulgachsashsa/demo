package org.example.demo.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.demo.entity.Minio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinioRepository extends JpaRepository<Minio, Long> {

    @Query("SELECT namePicture FROM Minio " +
            "WHERE driver.id = :driverId")
    List<String> findImageNamesByDriverId(@Param("driverId") Long driverId);
}
