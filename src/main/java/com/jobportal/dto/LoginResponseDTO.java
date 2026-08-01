package com.jobportal.dto;

public class LoginResponseDTO {

    private String message;
    private String role;
    private Long userId;
    private Long companyId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String message, String role, Long userId, Long companyId) {
        this.message = message;
        this.role = role;
        this.userId = userId;
        this.companyId = companyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}