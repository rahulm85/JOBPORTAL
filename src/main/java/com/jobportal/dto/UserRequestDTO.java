package com.jobportal.dto;

import com.jobportal.entity.Role;

import lombok.Data;
@Data 
public class UserRequestDTO {
	 String fullName;
	    String email;
	    String password;
	    String phone;
	    Role role;
	    
}
