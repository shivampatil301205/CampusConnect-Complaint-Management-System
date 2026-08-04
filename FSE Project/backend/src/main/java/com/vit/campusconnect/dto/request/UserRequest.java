package com.vit.campusconnect.dto.request;

import com.vit.campusconnect.enums.Role;

public class UserRequest {
    private String name;
    private String email;
    private Role role;

    public UserRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
