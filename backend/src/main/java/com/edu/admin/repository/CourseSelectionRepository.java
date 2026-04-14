package com.edu.admin.repository;

import com.edu.admin.entity.CourseSelection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSelectionRepository extends JpaRepository<CourseSelection, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<CourseSelection> findByStudentId(Long studentId);

    Optional<CourseSelection> findByIdAndStudentId(Long id, Long studentId);
}

