package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entity.Application;

public interface ApplicationRepository 
        extends JpaRepository<Application, Long> {


    // Candidate applications
    List<Application> findByCandidateId(Long candidateId);



    // Recruiter applications by company
    List<Application> findByJobCompanyId(Long companyId);



    // Check duplicate application
    boolean existsByCandidateIdAndJobId(
            Long candidateId,
            Long jobId
    );



    // Get applicants for a particular job
    List<Application> findByJobId(Long jobId);


}