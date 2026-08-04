package com.vit.campusconnect.controller;

import com.vit.campusconnect.dto.request.DepartmentRequest;
import com.vit.campusconnect.dto.response.DepartmentResponse;
import com.vit.campusconnect.service.DepartmentService;
import com.vit.campusconnect.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@RequestBody DepartmentRequest request) {
        DepartmentResponse resp = departmentService.createDepartment(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Department created successfully", resp));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        List<DepartmentResponse> list = departmentService.getAllDepartments();
        return ResponseEntity.ok(new ApiResponse<>(true, "Departments fetched successfully", list));
    }
}
