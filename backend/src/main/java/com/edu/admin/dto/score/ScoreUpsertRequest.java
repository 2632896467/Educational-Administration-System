package com.edu.admin.dto.score;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ScoreUpsertRequest {
    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Course id is required")
    private Long courseId;

    @NotNull(message = "Teacher id is required")
    private Long teacherId;

    @NotNull(message = "Score is required")
    @DecimalMin(value = "0.0", message = "Score must be >= 0")
    @DecimalMax(value = "100.0", message = "Score must be <= 100")
    private BigDecimal score;

    private String remark;
}

