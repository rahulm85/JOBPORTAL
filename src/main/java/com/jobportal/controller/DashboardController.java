package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.DashboardResponseDTO;
import com.jobportal.service.impl.DashboardService;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Candidate Dashboard
    @GetMapping("/dashboard/candidate/{candidateId}")
    public DashboardResponseDTO getCandidateDashboard(@PathVariable Long candidateId) {

        return dashboardService.getCandidateDashboard(candidateId);
    }

    // Recruiter Dashboard
    @GetMapping("/dashboard/recruiter/{companyId}")
    public DashboardResponseDTO getRecruiterDashboard(@PathVariable Long companyId) {

        return dashboardService.getRecruiterDashboard(companyId);
    }
}