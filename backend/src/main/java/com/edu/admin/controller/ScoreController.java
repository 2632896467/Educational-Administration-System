package com.edu.admin.controller;

import com.edu.admin.common.ApiResponse;
import com.edu.admin.dto.score.ScoreUpsertRequest;
import com.edu.admin.entity.Score;
import com.edu.admin.service.ScoreService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;

    @PostMapping
    public ApiResponse<Score> upsertScore(@Valid @RequestBody ScoreUpsertRequest request) {
        return ApiResponse.success(scoreService.upsertScore(request));
    }

    @GetMapping("/me")
    public ApiResponse<List<Score>> listMyScores(@RequestParam Long studentId) {
        return ApiResponse.success(scoreService.listByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ApiResponse<List<Score>> listByCourse(@PathVariable Long courseId) {
        return ApiResponse.success(scoreService.listByCourseId(courseId));
    }
}

