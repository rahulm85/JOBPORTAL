package com.jobportal.dto;

import com.jobportal.entity.Role;

import lombok.Data;
@Data
public class RegisterRequestDTO {
	String fullName;
    String email;
    String password;
    String phone;
    Role role;
    private String companyName;
    private String description;
    private String website;
    private String location;
}
