package com.vit.campusconnect.service;

import com.vit.campusconnect.dto.request.ComplaintRequest;
import com.vit.campusconnect.dto.response.ComplaintResponse;
import com.vit.campusconnect.entity.Complaint;
import com.vit.campusconnect.entity.User;
import com.vit.campusconnect.enums.Category;
import com.vit.campusconnect.enums.ComplaintStatus;
import com.vit.campusconnect.enums.Role;
import com.vit.campusconnect.repository.CommentRepository;
import com.vit.campusconnect.repository.ComplaintRepository;
import com.vit.campusconnect.repository.DepartmentRepository;
import com.vit.campusconnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ComplaintService complaintService;

    private User sampleUser;
    private Complaint sampleComplaint;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Shivam Patil");
        sampleUser.setEmail("student@vit.ac.in");
        sampleUser.setRole(Role.STUDENT);

        sampleComplaint = new Complaint();
        sampleComplaint.setId(101L);
        sampleComplaint.setTitle("Hostel Router Down");
        sampleComplaint.setDescription("No Internet access");
        sampleComplaint.setCategory(Category.HOSTEL);
        sampleComplaint.setStatus(ComplaintStatus.IN_PROGRESS);
        sampleComplaint.setStudent(sampleUser);
    }

    @Test
    void testCreateComplaint() {
        ComplaintRequest req = new ComplaintRequest();
        req.setTitle("Hostel Router Down");
        req.setDescription("No Internet access");
        req.setCategory(Category.HOSTEL);


        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(complaintRepository.save(any(Complaint.class))).thenReturn(sampleComplaint);

        ComplaintResponse response = complaintService.createComplaint(req, 1L);

        assertNotNull(response);
        assertEquals("Hostel Router Down", response.getTitle());
        assertEquals(ComplaintStatus.IN_PROGRESS, response.getStatus());
        assertEquals("Shivam Patil", response.getStudentName());
        verify(complaintRepository, times(1)).save(any(Complaint.class));
    }

    @Test
    void testGetAllComplaints() {
        when(complaintRepository.findAll()).thenReturn(Arrays.asList(sampleComplaint));

        List<ComplaintResponse> list = complaintService.getAllComplaints();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Hostel Router Down", list.get(0).getTitle());
    }

    @Test
    void testUpdateStatus() {
        when(complaintRepository.findById(101L)).thenReturn(Optional.of(sampleComplaint));
        when(complaintRepository.save(any(Complaint.class))).thenReturn(sampleComplaint);

        ComplaintResponse updated = complaintService.updateStatus(101L, ComplaintStatus.RESOLVED);

        assertNotNull(updated);
        assertEquals(ComplaintStatus.RESOLVED, updated.getStatus());
    }
}
