package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.dto.schedule.ScheduleCreateRequest;
import com.edu.admin.entity.CourseSchedule;
import com.edu.admin.repository.CourseRepository;
import com.edu.admin.repository.CourseScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final CourseRepository courseRepository;
    private final CourseScheduleRepository scheduleRepository;

    @Transactional
    public CourseSchedule createSchedule(ScheduleCreateRequest request) {
        if (request.getStartSection() > request.getEndSection()) {
            throw new BusinessException("Start section cannot be greater than end section");
        }
        courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BusinessException("Course does not exist"));

        CourseSchedule schedule = CourseSchedule.builder()
                .courseId(request.getCourseId())
                .classroom(request.getClassroom())
                .weekDay(request.getWeekDay())
                .startSection(request.getStartSection())
                .endSection(request.getEndSection())
                .weeks(request.getWeeks())
                .build();
        return scheduleRepository.save(schedule);
    }

    public List<CourseSchedule> listByCourseId(Long courseId) {
        return scheduleRepository.findByCourseId(courseId);
    }
}

