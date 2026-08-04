package com.vit.campusconnect.dto.request;

import com.vit.campusconnect.enums.Category;

public class ComplaintRequest {
    private String title;
    private String description;
    private Category category;
    private Long departmentId;

    public ComplaintRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }


    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}
