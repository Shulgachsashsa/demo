package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "driver")
public class Driver {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_phone", nullable = false, unique = true)
    private String numberPhone;

    @Column(name = "number_car", nullable = false, unique = true)
    private String numberCar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Minio> minio = new ArrayList<>();
}
