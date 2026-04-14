package com.edu.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
        name = "course",
        indexes = {
                @Index(name = "idx_course_semester", columnList = "semester"),
                @Index(name = "idx_course_teacher", columnList = "teacher_id")
        }
)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String semester;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private Integer capacity;

    @Builder.Default
    @Column(name = "selected_count", nullable = false)
    private Integer selectedCount = 0;

    @Version
    @Builder.Default
    @Column(nullable = false)
    private Long version = 0L;

    @PrePersist
    public void prePersist() {
        if (selectedCount == null) {
            selectedCount = 0;
        }
        if (version == null) {
            version = 0L;
        }
    }
}

