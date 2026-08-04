package com.vit.campusconnect.config;

import com.vit.campusconnect.entity.Complaint;
import com.vit.campusconnect.entity.ComplaintComment;
import com.vit.campusconnect.entity.Department;
import com.vit.campusconnect.entity.User;
import com.vit.campusconnect.enums.Category;
import com.vit.campusconnect.enums.ComplaintStatus;
import com.vit.campusconnect.enums.Role;
import com.vit.campusconnect.repository.CommentRepository;
import com.vit.campusconnect.repository.ComplaintRepository;
import com.vit.campusconnect.repository.DepartmentRepository;
import com.vit.campusconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Data already initialized
        }

        // 1. Create Default Users
        User student1 = new User();
        student1.setName("Shivam Patil");
        student1.setEmail("student@vit.ac.in");
        student1.setPassword(passwordEncoder.encode("student123"));
        student1.setRole(Role.STUDENT);
        student1 = userRepository.save(student1);

        User student2 = new User();
        student2.setName("Ananya Sharma");
        student2.setEmail("ananya@vit.ac.in");
        student2.setPassword(passwordEncoder.encode("student123"));
        student2.setRole(Role.STUDENT);
        student2 = userRepository.save(student2);

        User admin = new User();
        admin.setName("IT Department Admin");
        admin.setEmail("admin@vit.ac.in");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        admin = userRepository.save(admin);


        // 2. Create Default Departments
        Department d1 = new Department();
        d1.setName("IT & Infrastructure");
        d1.setDescription("Campus Wi-Fi, Routers, Servers, and Digital Lab Equipment");
        d1 = departmentRepository.save(d1);

        Department d2 = new Department();
        d2.setName("Maintenance & Civil");
        d2.setDescription("Plumbing, Electricals, Building Repairs, and Campus Cleaning");
        d2 = departmentRepository.save(d2);

        Department d3 = new Department();
        d3.setName("Academic Labs & Library");
        d3.setDescription("Library books, Lab computers, and Audio-Visual Hardware");
        d3 = departmentRepository.save(d3);

        Department d4 = new Department();
        d4.setName("Hostel Warden & Management");
        d4.setDescription("Hostel Room Allocations, Mess Hygiene, and Warden Oversight");
        d4 = departmentRepository.save(d4);

        // 3. Create Sample Complaints & Comments
        Complaint c1 = new Complaint();
        c1.setTitle("Hostel Block-B Wi-Fi Not Working");
        c1.setDescription("The Wi-Fi router on the 3rd floor of Hostel Block B has been offline since yesterday evening. Students are unable to access study materials.");
        c1.setCategory(Category.HOSTEL);

        c1.setStatus(ComplaintStatus.IN_PROGRESS);
        c1.setStudent(student1);
        c1.setDepartment(d1);
        c1 = complaintRepository.save(c1);

        ComplaintComment c1_rem1 = new ComplaintComment("Please resolve this urgently as exams are approaching.", student1, c1);
        commentRepository.save(c1_rem1);

        ComplaintComment c1_rem2 = new ComplaintComment("Technician assigned. Replacing router firmware.", admin, c1);
        commentRepository.save(c1_rem2);

        Complaint c2 = new Complaint();
        c2.setTitle("Water Leakage in Main Library Restroom");
        c2.setDescription("Pipe leakage near the 2nd floor library entrance causing slippery floor and water wastage.");
        c2.setCategory(Category.INFRASTRUCTURE);
        c2.setStatus(ComplaintStatus.IN_PROGRESS);
        c2.setStudent(student2);
        c2.setDepartment(d2);
        c2 = complaintRepository.save(c2);

        Complaint c3 = new Complaint();
        c3.setTitle("Lab 4 Computer Monitor Flicker");
        c3.setDescription("Monitor #14 in Computer Lab 4 blinks continuously. Needs display cable replacement.");
        c3.setCategory(Category.ACADEMIC);

        c3.setStatus(ComplaintStatus.RESOLVED);
        c3.setStudent(student1);
        c3.setDepartment(d3);
        c3 = complaintRepository.save(c3);

        ComplaintComment c3_rem1 = new ComplaintComment("Replaced VGA cable with HDMI. Issue fixed.", admin, c3);
        commentRepository.save(c3_rem1);
    }
}
