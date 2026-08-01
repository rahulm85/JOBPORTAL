package com.jobportal.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.LoginRequestDTO;
import com.jobportal.dto.LoginResponseDTO;
import com.jobportal.dto.RegisterRequestDTO;
import com.jobportal.entity.Company;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.dto.UserResponseDTO;
@Service
public class UserService {
	 @Autowired
	    private UserRepository userRepository;
	 @Autowired
	 private CompanyRepository  companyRepository;

	 public String register(RegisterRequestDTO request) {

		    // Check if email already exists
		    Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		    if (existingUser.isPresent()) {
		        return "Email already exists";
		    }

		    // Create User
		    User user = new User();
		    user.setFullName(request.getFullName());
		    user.setEmail(request.getEmail());
		    user.setPassword(request.getPassword());
		    user.setPhone(request.getPhone());
		    user.setRole(request.getRole());

		    // Save User first
		    user = userRepository.save(user);

		    // If recruiter, create company also
		    if (request.getRole() == Role.RECRUITER) {

		        Company company = new Company();

		        company.setCompanyName(request.getCompanyName());
		        company.setDescription(request.getDescription());
		        company.setWebsite(request.getWebsite());
		        company.setLocation(request.getLocation());

		        // Link Company with Recruiter
		        company.setRecruiter(user);

		        companyRepository.save(company);
		    }

		    return "Registration Successful";
		}
	 public LoginResponseDTO login(LoginRequestDTO request) {

		    User user = userRepository.findByEmail(request.getEmail()).orElse(null);

		    if (user == null) {
		        return new LoginResponseDTO(
		                "User not found",
		                null,
		                null,
		                null
		        );
		    }

		    if (!user.getPassword().equals(request.getPassword())) {
		        return new LoginResponseDTO(
		                "Invalid password",
		                null,
		                null,
		                null
		        );
		    }

		    Long companyId = null;

		    if (user.getRole() == Role.RECRUITER) {

		        Company company = companyRepository.findByRecruiter(user);

		        if (company != null) {
		            companyId = company.getId();
		        }
		    }

		    return new LoginResponseDTO(
		            "Login Successful",
		            user.getRole().name(),
		            user.getId(),
		            companyId
		    );
		}
	// Get User Details
	 public UserResponseDTO getUserById(Long id) {

	     User user = userRepository.findById(id).orElse(null);

	     if (user == null) {
	         return null;
	     }

	     UserResponseDTO dto = new UserResponseDTO();

	     dto.setId(user.getId());
	     dto.setFullName(user.getFullName());
	     dto.setEmail(user.getEmail());
	     dto.setPhone(user.getPhone());

	     return dto;
	 }
}