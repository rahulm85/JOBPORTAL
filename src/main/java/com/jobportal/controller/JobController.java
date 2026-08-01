package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobportal.dto.JobRequestDTO;
import com.jobportal.dto.JobResponseDTO;
import com.jobportal.entity.Job;
import com.jobportal.service.impl.JobService;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;


    // Create Job
    @PostMapping("/jobs")
    public String createJob(@RequestBody JobRequestDTO request) {

        return jobService.createJob(request);

    }



    // Get All Jobs
    @GetMapping("/jobs")
    public List<JobResponseDTO> getAllJobs() {

        return jobService.getAllJobs();

    }



    // Get Jobs By Company
    // Used in Recruiter Dashboard
    @GetMapping("/jobs/company/{companyId}")
    public List<JobResponseDTO> getJobsByCompany(
            @PathVariable Long companyId) {

        return jobService.getJobsByCompany(companyId);

    }




    // Get Job By Id
    @GetMapping("/jobs/{id}")
    public JobResponseDTO getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id);

    }




    // Update Job
    @PutMapping("/jobs/{id}")
    public String updateJob(
            @PathVariable Long id,
            @RequestBody JobRequestDTO request) {

        return jobService.updateJob(id, request);

    }




    // Delete Job
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(
            @PathVariable Long id) {

        return jobService.deleteJob(id);

    }




    // Search Job By Title
    @GetMapping("/jobs/title/{title}")
    public List<Job> searchByTitle(
            @PathVariable String title) {

        return jobService.searchByTitle(title);

    }




    // Search Job By Location
    @GetMapping("/jobs/location/{location}")
    public List<Job> searchByLocation(
            @PathVariable String location) {

        return jobService.searchByLocation(location);

    }




    // Search Job By Category
    @GetMapping("/jobs/category/{category}")
    public List<Job> searchByCategory(
            @PathVariable String category) {

        return jobService.searchByCategory(category);

    }




    // Search Job By Job Type
    @GetMapping("/jobs/type/{jobType}")
    public List<Job> searchByJobType(
            @PathVariable String jobType) {

        return jobService.searchByJobType(jobType);

    }

}