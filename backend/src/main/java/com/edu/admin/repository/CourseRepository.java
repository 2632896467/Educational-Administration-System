package com.edu.admin.repository;

import com.edu.admin.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findBySemester(String semester);

    boolean existsByCode(String code);

    @Modifying
    @Query("""
            update Course c
               set c.selectedCount = c.selectedCount + 1,
                   c.version = c.version + 1
             where c.id = :courseId
               and c.version = :version
               and c.selectedCount < c.capacity
            """)
    int incrementSeatWithVersion(@Param("courseId") Long courseId, @Param("version") Long version);

    @Modifying
    @Query("""
            update Course c
               set c.selectedCount = c.selectedCount - 1,
                   c.version = c.version + 1
             where c.id = :courseId
               and c.version = :version
               and c.selectedCount > 0
            """)
    int decrementSeatWithVersion(@Param("courseId") Long courseId, @Param("version") Long version);
}

