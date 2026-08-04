package com.vit.campusconnect.service;

import com.vit.campusconnect.dto.request.DepartmentRequest;
import com.vit.campusconnect.dto.response.DepartmentResponse;
import com.vit.campusconnect.entity.Department;
import com.vit.campusconnect.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department dept = new Department();
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        dept = departmentRepository.save(dept);

        DepartmentResponse resp = new DepartmentResponse();
        resp.setId(dept.getId());
        resp.setName(dept.getName());
        resp.setDescription(dept.getDescription());
        return resp;
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream().map(dept -> {
            DepartmentResponse resp = new DepartmentResponse();
            resp.setId(dept.getId());
            resp.setName(dept.getName());
            resp.setDescription(dept.getDescription());
            return resp;
        }).collect(Collectors.toList());
    }
}
