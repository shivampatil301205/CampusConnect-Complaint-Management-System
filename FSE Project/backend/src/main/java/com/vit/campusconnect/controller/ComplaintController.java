package com.vit.campusconnect.controller;

import com.vit.campusconnect.config.CurrentUserId;
import com.vit.campusconnect.dto.request.CommentRequest;
import com.vit.campusconnect.dto.request.ComplaintRequest;
import com.vit.campusconnect.dto.response.CommentResponse;
import com.vit.campusconnect.dto.response.ComplaintResponse;
import com.vit.campusconnect.enums.ComplaintStatus;
import com.vit.campusconnect.service.ComplaintService;
import com.vit.campusconnect.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ApiResponse<ComplaintResponse>> createComplaint(
            @RequestBody ComplaintRequest request,
            @CurrentUserId Long userId) {
        ComplaintResponse resp = complaintService.createComplaint(request, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Complaint created successfully", resp));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getMyComplaints(@CurrentUserId Long userId) {
        List<ComplaintResponse> list = complaintService.getComplaintsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "My complaints fetched successfully", list));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getAllComplaints() {
        List<ComplaintResponse> list = complaintService.getAllComplaints();
        return ResponseEntity.ok(new ApiResponse<>(true, "All complaints fetched successfully", list));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ComplaintResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam ComplaintStatus status) {
        ComplaintResponse resp = complaintService.updateStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated successfully", resp));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request,
            @CurrentUserId Long userId) {
        CommentResponse resp = complaintService.addComment(id, request, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Comment added successfully", resp));
    }
}
