package com.edu.admin.dto.selection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SelectCourseRequest {
    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Course id is required")
    private Long courseId;
}

