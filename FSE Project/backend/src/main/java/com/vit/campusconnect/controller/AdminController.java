package com.vit.campusconnect.controller;

import com.vit.campusconnect.dto.response.ComplaintResponse;
import com.vit.campusconnect.service.ComplaintService;
import com.vit.campusconnect.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getAdminDashboard() {
        List<ComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin dashboard data loaded", complaints));
    }
}
