package com.edu.admin.controller;

import com.edu.admin.common.ApiResponse;
import com.edu.admin.dto.course.CourseCreateRequest;
import com.edu.admin.dto.schedule.ScheduleCreateRequest;
import com.edu.admin.dto.user.UserCreateRequest;
import com.edu.admin.entity.Course;
import com.edu.admin.entity.CourseSchedule;
import com.edu.admin.entity.User;
import com.edu.admin.service.CourseService;
import com.edu.admin.service.ScheduleService;
import com.edu.admin.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CourseService courseService;
    private final ScheduleService scheduleService;

    @PostMapping("/users")
    public ApiResponse<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        return ApiResponse.success(userService.createUser(request));
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> listUsers() {
        return ApiResponse.success(userService.listUsers());
    }

    @PostMapping("/courses")
    public ApiResponse<Course> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        return ApiResponse.success(courseService.createCourse(request));
    }

    @PostMapping("/schedules")
    public ApiResponse<CourseSchedule> createSchedule(@Valid @RequestBody ScheduleCreateRequest request) {
        return ApiResponse.success(scheduleService.createSchedule(request));
    }

    @GetMapping("/schedules/course/{courseId}")
    public ApiResponse<List<CourseSchedule>> listScheduleByCourse(@PathVariable Long courseId) {
        return ApiResponse.success(scheduleService.listByCourseId(courseId));
    }
}

