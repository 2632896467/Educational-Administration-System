package com.edu.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "course_schedule",
        indexes = {
                @Index(name = "idx_schedule_course", columnList = "course_id")
        }
)
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(nullable = false, length = 50)
    private String classroom;

    @Column(name = "week_day", nullable = false)
    private Integer weekDay;

    @Column(name = "start_section", nullable = false)
    private Integer startSection;

    @Column(name = "end_section", nullable = false)
    private Integer endSection;

    @Column(nullable = false, length = 30)
    private String weeks;
}

