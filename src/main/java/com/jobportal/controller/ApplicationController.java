package com.jobportal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.service.impl.ApplicationService;


@RestController
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;




    // APPLY JOB WITH RESUME

    @PostMapping("/application")
    public String applyJob(

            @RequestParam Long candidateId,

            @RequestParam Long jobId,

            @RequestParam String coverLetter,

            @RequestParam MultipartFile resume

    ) {


        return applicationService.applyJob(
                candidateId,
                jobId,
                coverLetter,
                resume
        );

    }







    // GET CANDIDATE APPLICATIONS

    @GetMapping("/applications/candidate/{candidateId}")
    public List<ApplicationResponseDTO> getApplicationsByCandidate(
            @PathVariable Long candidateId
    ) {


        return applicationService
                .getApplicationsByCandidate(candidateId);

    }








    // UPDATE APPLICATION STATUS

    @PutMapping("/application/{id}/status")
    public String updateStatus(

            @PathVariable Long id,

            @RequestParam String status

    ) {


        return applicationService
                .updateStatus(id, status);

    }







    // CHECK APPLICATION

    @GetMapping("/application/check")
    public boolean checkApplication(

            @RequestParam Long candidateId,

            @RequestParam Long jobId

    ) {


        return applicationService
                .hasApplied(candidateId, jobId);

    }
    @GetMapping("/applications/job/{jobId}")
    public List<ApplicationResponseDTO> getApplicantsByJob(
            @PathVariable Long jobId
    ){

        return applicationService.getApplicantsByJob(jobId);

    }
    @GetMapping("/applications/company/{companyId}")
    public List<ApplicationResponseDTO> getApplicationsByCompany(
            @PathVariable Long companyId
    ) {

        return applicationService
                .getApplicationsByCompany(companyId);

    }

}