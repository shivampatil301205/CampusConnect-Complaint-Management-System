package com.vit.campusconnect.service;

import com.vit.campusconnect.dto.request.CommentRequest;
import com.vit.campusconnect.dto.request.ComplaintRequest;
import com.vit.campusconnect.dto.response.CommentResponse;
import com.vit.campusconnect.dto.response.ComplaintResponse;
import com.vit.campusconnect.entity.Complaint;
import com.vit.campusconnect.entity.ComplaintComment;
import com.vit.campusconnect.entity.Department;
import com.vit.campusconnect.entity.User;
import com.vit.campusconnect.enums.ComplaintStatus;
import com.vit.campusconnect.repository.CommentRepository;
import com.vit.campusconnect.repository.ComplaintRepository;
import com.vit.campusconnect.repository.DepartmentRepository;
import com.vit.campusconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Transactional
    public ComplaintResponse createComplaint(ComplaintRequest request, Long userId) {
        User student = userId != null ? userRepository.findById(userId).orElse(null) : null;
        if (student == null) {
            student = userRepository.findAll().stream().findFirst().orElse(null);
        }

        Department dept = null;
        if (request.getDepartmentId() != null) {
            dept = departmentRepository.findById(request.getDepartmentId()).orElse(null);
        }

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setStatus(ComplaintStatus.IN_PROGRESS);
        complaint.setStudent(student);
        complaint.setDepartment(dept);

        complaint = complaintRepository.save(complaint);
        return mapToResponse(complaint);
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getComplaintsByUser(Long userId) {
        return complaintRepository.findByStudentId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ComplaintResponse updateStatus(Long complaintId, ComplaintStatus status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));
        complaint.setStatus(status);
        complaint = complaintRepository.save(complaint);
        return mapToResponse(complaint);
    }

    @Transactional
    public CommentResponse addComment(Long complaintId, CommentRequest request, Long userId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + complaintId));

        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        if (user == null) {
            user = userRepository.findAll().stream().findFirst().orElse(null);
        }

        ComplaintComment comment = new ComplaintComment(request.getText(), user, complaint);
        comment = commentRepository.save(comment);

        String authorName = user != null ? user.getName() : "Anonymous User";
        String roleStr = user != null && user.getRole() != null ? user.getRole().name() : "STUDENT";
        String timeStr = comment.getCreatedAt() != null ? comment.getCreatedAt().format(FORMATTER) : "";

        return new CommentResponse(comment.getId(), authorName, roleStr, comment.getText(), timeStr);
    }

    public ComplaintResponse mapToResponse(Complaint complaint) {
        ComplaintResponse resp = new ComplaintResponse();
        resp.setId(complaint.getId());
        resp.setTitle(complaint.getTitle());
        resp.setDescription(complaint.getDescription());
        resp.setCategory(complaint.getCategory());
        resp.setStatus(complaint.getStatus());
        
        if (complaint.getCreatedAt() != null) {
            resp.setCreatedAt(complaint.getCreatedAt().format(FORMATTER));
        }

        if (complaint.getStudent() != null) {
            resp.setStudentId(complaint.getStudent().getId());
            resp.setStudentName(complaint.getStudent().getName());
            resp.setStudentEmail(complaint.getStudent().getEmail());
        }

        if (complaint.getDepartment() != null) {
            resp.setDepartmentId(complaint.getDepartment().getId());
            resp.setDepartmentName(complaint.getDepartment().getName());
        } else {
            resp.setDepartmentName("General Maintenance");
        }

        if (complaint.getComments() != null) {
            List<CommentResponse> commentResponses = complaint.getComments().stream()
                    .map(c -> {
                        String authorName = c.getAuthor() != null ? c.getAuthor().getName() : "User";
                        String roleStr = c.getAuthor() != null && c.getAuthor().getRole() != null ? c.getAuthor().getRole().name() : "STUDENT";
                        String timeStr = c.getCreatedAt() != null ? c.getCreatedAt().format(FORMATTER) : "";
                        return new CommentResponse(c.getId(), authorName, roleStr, c.getText(), timeStr);
                    })
                    .collect(Collectors.toList());
            resp.setComments(commentResponses);
        }

        return resp;
    }
}
