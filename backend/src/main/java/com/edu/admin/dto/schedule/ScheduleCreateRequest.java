package com.edu.admin.dto.schedule;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleCreateRequest {
    @NotNull(message = "Course id is required")
    private Long courseId;

    @NotBlank(message = "Classroom cannot be blank")
    private String classroom;

    @NotNull(message = "Week day is required")
    @Min(value = 1, message = "Week day must be between 1 and 7")
    @Max(value = 7, message = "Week day must be between 1 and 7")
    private Integer weekDay;

    @NotNull(message = "Start section is required")
    @Min(value = 1, message = "Start section must be >= 1")
    private Integer startSection;

    @NotNull(message = "End section is required")
    @Min(value = 1, message = "End section must be >= 1")
    private Integer endSection;

    @NotBlank(message = "Weeks cannot be blank")
    private String weeks;
}

