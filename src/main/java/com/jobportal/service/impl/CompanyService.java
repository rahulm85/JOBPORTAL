package com.jobportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.CompanyRequestDTO;
import com.jobportal.dto.CompanyResponseDTO;
import com.jobportal.entity.Company;
import com.jobportal.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	public CompanyResponseDTO getCompany(Long id) {

	    Company company = companyRepository.findById(id).orElse(null);

	    if (company == null) {
	        return null;
	    }

	    CompanyResponseDTO response = new CompanyResponseDTO();

	    response.setId(company.getId());
	    response.setCompanyName(company.getCompanyName());
	    response.setDescription(company.getDescription());
	    response.setWebsite(company.getWebsite());
	    response.setLocation(company.getLocation());

	    return response;
	}
	public String updateCompany(Long id, CompanyRequestDTO request) {

	    Company company = companyRepository.findById(id).orElse(null);

	    if (company == null) {
	        return "Company not found";
	    }

	    company.setCompanyName(request.getCompanyName());
	    company.setDescription(request.getDescription());
	    company.setWebsite(request.getWebsite());
	    company.setLocation(request.getLocation());

	    companyRepository.save(company);

	    return "Company Updated Successfully";
	}

}
