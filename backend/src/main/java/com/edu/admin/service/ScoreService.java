package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.score.ScoreUpsertRequest;
import com.edu.admin.entity.Score;
import com.edu.admin.repository.CourseSelectionRepository;
import com.edu.admin.repository.ScoreRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final CourseSelectionRepository selectionRepository;

    @Transactional
    public Score upsertScore(ScoreUpsertRequest request) {
        if (!selectionRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new BusinessException("Student has not selected this course");
        }

        Score score = scoreRepository
                .findByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())
                .orElseGet(() -> Score.builder()
                        .studentId(request.getStudentId())
                        .courseId(request.getCourseId())
                        .build());

        score.setTeacherId(request.getTeacherId());
        score.setScore(request.getScore());
        score.setGradePoint(calculateGradePoint(request.getScore()));
        score.setRemark(request.getRemark());

        return scoreRepository.save(score);
    }

    public List<Score> listByStudentId(Long studentId) {
        return scoreRepository.findByStudentId(studentId);
    }

    public List<Score> listByCourseId(Long courseId) {
        return scoreRepository.findByCourseId(courseId);
    }

    private BigDecimal calculateGradePoint(BigDecimal score) {
        double value = score.doubleValue();
        double gp;
        if (value >= 90) {
            gp = 4.0;
        } else if (value >= 85) {
            gp = 3.7;
        } else if (value >= 82) {
            gp = 3.3;
        } else if (value >= 78) {
            gp = 3.0;
        } else if (value >= 75) {
            gp = 2.7;
        } else if (value >= 72) {
            gp = 2.3;
        } else if (value >= 68) {
            gp = 2.0;
        } else if (value >= 64) {
            gp = 1.5;
        } else if (value >= 60) {
            gp = 1.0;
        } else {
            gp = 0.0;
        }
        return BigDecimal.valueOf(gp).setScale(1, RoundingMode.HALF_UP);
    }
}

