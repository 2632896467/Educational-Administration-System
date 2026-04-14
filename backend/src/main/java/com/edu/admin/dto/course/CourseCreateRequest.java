package com.edu.admin.dto.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseCreateRequest {
    @NotBlank(message = "Course code cannot be blank")
    private String code;

    @NotBlank(message = "Course name cannot be blank")
    private String name;

    @NotBlank(message = "Semester cannot be blank")
    private String semester;

    @NotNull(message = "Teacher id is required")
    private Long teacherId;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;
}

