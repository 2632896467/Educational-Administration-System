package com.edu.admin.repository;

import com.edu.admin.entity.CourseSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    List<CourseSchedule> findByCourseId(Long courseId);
}

