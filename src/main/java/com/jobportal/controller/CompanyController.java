package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.CompanyRequestDTO;
import com.jobportal.dto.CompanyResponseDTO;
import com.jobportal.service.impl.CompanyService;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company/{id}")
    public CompanyResponseDTO getCompany(@PathVariable Long id) {
        return companyService.getCompany(id);
    }

    @PutMapping("/company/{id}")
    public String updateCompany(@PathVariable Long id,
                                @RequestBody CompanyRequestDTO request) {

        return companyService.updateCompany(id, request);
    }
}