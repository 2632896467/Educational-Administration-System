package com.edu.admin.controller;

import com.edu.admin.common.ApiResponse;
import com.edu.admin.dto.selection.SelectCourseRequest;
import com.edu.admin.entity.CourseSelection;
import com.edu.admin.service.SelectionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/selections")
@RequiredArgsConstructor
public class SelectionController {
    private final SelectionService selectionService;

    @PostMapping
    public ApiResponse<Long> selectCourse(@Valid @RequestBody SelectCourseRequest request) {
        Long selectionId = selectionService.selectCourse(request.getStudentId(), request.getCourseId());
        return ApiResponse.success(selectionId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> dropCourse(@PathVariable("id") Long selectionId, @RequestParam Long studentId) {
        selectionService.dropCourse(studentId, selectionId);
        return ApiResponse.successMessage("Dropped successfully");
    }

    @GetMapping
    public ApiResponse<List<CourseSelection>> listMySelections(@RequestParam Long studentId) {
        return ApiResponse.success(selectionService.listByStudentId(studentId));
    }
}

