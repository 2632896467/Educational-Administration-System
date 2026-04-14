package com.edu.admin.repository;

import com.edu.admin.entity.Score;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Score> findByStudentId(Long studentId);

    List<Score> findByCourseId(Long courseId);
}

