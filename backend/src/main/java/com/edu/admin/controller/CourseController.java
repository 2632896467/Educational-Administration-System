package com.edu.admin.controller;

import com.edu.admin.common.ApiResponse;
import com.edu.admin.entity.Course;
import com.edu.admin.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ApiResponse<List<Course>> listCourses(@RequestParam(required = false) String semester) {
        return ApiResponse.success(courseService.listCourses(semester));
    }
}

