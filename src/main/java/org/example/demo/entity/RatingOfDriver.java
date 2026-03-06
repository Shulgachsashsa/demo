package org.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rating_of_driver")
public class RatingOfDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // --- Количество всех поездок --- //
    @Column(name = "total_counter_trip", columnDefinition = "INTEGER DEFAULT 0")
    private int totalCounterTrip;

    // --- Средняя оценка -> total_grades / total_grade_counter --- //
    @Column(name = "average_grade")
    private double averageGrade;

    // --- Кол-во всех оценок --- 1-4 2-5 3-4 4-5 -> 4 //
    @Column(name = "total_grade_counter")
    private int totalGradeCounter;

    // --- Общее кол-во всех оценок -> 4 + 5 + 4 + 4 -> 17 //
    @Column(name = "total_grades")
    private int totalGrades;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;

}
