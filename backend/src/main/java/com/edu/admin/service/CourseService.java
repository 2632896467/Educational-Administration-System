package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.course.CourseCreateRequest;
import com.edu.admin.entity.Course;
import com.edu.admin.repository.CourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> listCourses(String semester) {
        if (StringUtils.hasText(semester)) {
            return courseRepository.findBySemester(semester);
        }
        return courseRepository.findAll();
    }

    @Transactional
    public Course createCourse(CourseCreateRequest request) {
        if (courseRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Course code already exists");
        }
        Course course = Course.builder()
                .code(request.getCode())
                .name(request.getName())
                .semester(request.getSemester())
                .teacherId(request.getTeacherId())
                .capacity(request.getCapacity())
                .selectedCount(0)
                .version(0L)
                .build();
        return courseRepository.save(course);
    }
}

