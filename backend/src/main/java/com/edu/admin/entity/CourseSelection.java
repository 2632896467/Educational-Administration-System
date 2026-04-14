package com.edu.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
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
        name = "course_selection",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_selection_student_course",
                        columnNames = {"student_id", "course_id"}
                )
        },
        indexes = {
                @Index(name = "idx_selection_student", columnList = "student_id"),
                @Index(name = "idx_selection_course", columnList = "course_id")
        }
)
public class CourseSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "selected_at", nullable = false)
    private LocalDateTime selectedAt;

    @PrePersist
    public void prePersist() {
        if (selectedAt == null) {
            selectedAt = LocalDateTime.now();
        }
    }
}

