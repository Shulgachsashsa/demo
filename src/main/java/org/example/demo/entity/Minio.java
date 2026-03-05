package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "minio")
public class Minio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_picture", nullable = false)
    private String namePicture;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
