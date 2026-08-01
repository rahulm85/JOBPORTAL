package com.jobportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.DashboardResponseDTO;
import com.jobportal.entity.Application;
import com.jobportal.entity.Company;
import com.jobportal.entity.Job;
import com.jobportal.entity.SavedJob;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.SavedJobRepository;
import com.jobportal.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private SavedJobRepository savedJobRepository;

    // Candidate Dashboard
    public DashboardResponseDTO getCandidateDashboard(Long candidateId) {

        User user = userRepository.findById(candidateId).orElse(null);

        if (user == null) {
            return null;
        }

        List<Application> applications =
                applicationRepository.findByCandidateId(candidateId);

        List<SavedJob> savedJobs =
                savedJobRepository.findByCandidateId(candidateId);

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setName(user.getFullName());
        dto.setTotalJobs(applications.size());
        dto.setTotalApplications(applications.size());
        dto.setTotalSavedJobs(savedJobs.size());

        return dto;
    }

    // Recruiter Dashboard
    public DashboardResponseDTO getRecruiterDashboard(Long companyId) {

        Company company = companyRepository.findById(companyId).orElse(null);

        if (company == null) {
            return null;
        }

        List<Job> jobs =
                jobRepository.findByCompanyId(companyId);

        List<Application> applications =
                applicationRepository.findByJobCompanyId(companyId);

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setName(company.getCompanyName());
        dto.setTotalJobs(jobs.size());
        dto.setTotalApplications(applications.size());
        dto.setTotalSavedJobs(0);

        return dto;
    }
}