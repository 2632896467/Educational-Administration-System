package com.edu.admin.service;

import com.edu.admin.common.exception.BusinessException;
import com.edu.admin.common.exception.OptimisticLockConflictException;
import com.edu.admin.entity.Course;
import com.edu.admin.entity.CourseSelection;
import com.edu.admin.repository.CourseRepository;
import com.edu.admin.repository.CourseSelectionRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class SelectionService {
    private static final int MAX_RETRY = 5;

    private final CourseRepository courseRepository;
    private final CourseSelectionRepository selectionRepository;
    private final TransactionTemplate transactionTemplate;

    public SelectionService(
            CourseRepository courseRepository,
            CourseSelectionRepository selectionRepository,
            PlatformTransactionManager transactionManager
    ) {
        this.courseRepository = courseRepository;
        this.selectionRepository = selectionRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public Long selectCourse(Long studentId, Long courseId) {
        for (int i = 0; i < MAX_RETRY; i++) {
            try {
                Long selectionId = transactionTemplate.execute(status -> selectCourseInTx(studentId, courseId));
                if (selectionId != null) {
                    return selectionId;
                }
            } catch (OptimisticLockConflictException ex) {
                if (i == MAX_RETRY - 1) {
                    throw new BusinessException("Selection is busy, please retry");
                }
            } catch (DataIntegrityViolationException ex) {
                throw new BusinessException("Duplicate selection is not allowed");
            }
        }
        throw new BusinessException("Selection failed, please retry later");
    }

    public void dropCourse(Long studentId, Long selectionId) {
        for (int i = 0; i < MAX_RETRY; i++) {
            try {
                Boolean ok = transactionTemplate.execute(status -> {
                    dropCourseInTx(studentId, selectionId);
                    return true;
                });
                if (Boolean.TRUE.equals(ok)) {
                    return;
                }
            } catch (OptimisticLockConflictException ex) {
                if (i == MAX_RETRY - 1) {
                    throw new BusinessException("Drop operation is busy, please retry");
                }
            }
        }
        throw new BusinessException("Drop course failed, please retry later");
    }

    public List<CourseSelection> listByStudentId(Long studentId) {
        return selectionRepository.findByStudentId(studentId);
    }

    private Long selectCourseInTx(Long studentId, Long courseId) {
        if (selectionRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BusinessException("You have already selected this course");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException("Course does not exist"));
        if (course.getSelectedCount() >= course.getCapacity()) {
            throw new BusinessException("Course is full");
        }

        int updated = courseRepository.incrementSeatWithVersion(courseId, course.getVersion());
        if (updated == 0) {
            throw new OptimisticLockConflictException("Optimistic lock conflict");
        }

        CourseSelection selection = CourseSelection.builder()
                .studentId(studentId)
                .courseId(courseId)
                .build();
        selection = selectionRepository.save(selection);
        return selection.getId();
    }

    private void dropCourseInTx(Long studentId, Long selectionId) {
        CourseSelection selection = selectionRepository.findByIdAndStudentId(selectionId, studentId)
                .orElseThrow(() -> new BusinessException("Selection record does not exist"));

        Course course = courseRepository.findById(selection.getCourseId())
                .orElseThrow(() -> new BusinessException("Course does not exist"));

        int updated = courseRepository.decrementSeatWithVersion(course.getId(), course.getVersion());
        if (updated == 0) {
            throw new OptimisticLockConflictException("Optimistic lock conflict");
        }

        selectionRepository.delete(selection);
    }
}

