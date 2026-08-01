package com.jobportal.service.impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;


@Service
public class ApplicationService {


    @Autowired
    private ApplicationRepository applicationRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JobRepository jobRepository;



    private final String uploadDir =
            "C:/SpringBoot/JOBPORTAL_UPLOADS/resumes/";



    // APPLY JOB WITH RESUME UPLOAD

    public String applyJob(
            Long candidateId,
            Long jobId,
            String coverLetter,
            MultipartFile resume
    ) {



        boolean alreadyApplied =
                applicationRepository.existsByCandidateIdAndJobId(
                        candidateId,
                        jobId
                );


        if(alreadyApplied){

            return "You already applied for this job";

        }



        User candidate =
                userRepository.findById(candidateId)
                .orElse(null);



        if(candidate == null){

            return "Candidate Not Found";

        }




        Job job =
                jobRepository.findById(jobId)
                .orElse(null);



        if(job == null){

            return "Job Not Found";

        }




        String fileName =
                System.currentTimeMillis()
                + "_"
                + resume.getOriginalFilename();



        try {


            Path path =
            Paths.get(uploadDir + fileName);



            Files.createDirectories(
                    path.getParent()
            );



            Files.copy(
                    resume.getInputStream(),
                    path
            );


        }
        catch(IOException e){

            e.printStackTrace();

            return "Resume Upload Failed";

        }




        Application application =
                new Application();



        application.setCandidate(candidate);

        application.setJob(job);

        application.setStatus("APPLIED");

        application.setCoverLetter(coverLetter);


        application.setResumePath(
                "uploads/resumes/" + fileName
        );



        applicationRepository.save(application);



        return "Application Submitted Successfully";


    }







    // GET APPLICATIONS BY CANDIDATE

    public List<ApplicationResponseDTO>
    getApplicationsByCandidate(Long candidateId){



        List<Application> applications =
                applicationRepository
                .findByCandidateId(candidateId);



        List<ApplicationResponseDTO> response =
                new ArrayList<>();



        for(Application application : applications){


            ApplicationResponseDTO dto =
                    new ApplicationResponseDTO();



            dto.setId(application.getId());


            dto.setCandidateName(
                    application.getCandidate()
                    .getFullName()
            );


            dto.setJobTitle(
                    application.getJob()
                    .getTitle()
            );


            dto.setStatus(
                    application.getStatus()
            );


            dto.setAppliedDate(
                    application.getAppliedDate()
            );



            response.add(dto);


        }



        return response;


    }








    // UPDATE APPLICATION STATUS

    public String updateStatus(
            Long id,
            String status
    ){


        Application application =
                applicationRepository
                .findById(id)
                .orElse(null);



        if(application == null){

            return "Application Not Found";

        }



        application.setStatus(status);


        applicationRepository.save(application);



        return "Status Updated Successfully";


    }







    // CHECK IF ALREADY APPLIED

    public boolean hasApplied(
            Long candidateId,
            Long jobId
    ){


        return applicationRepository
                .existsByCandidateIdAndJobId(
                        candidateId,
                        jobId
                );


    }


 // GET APPLICANTS BY JOB

    public List<ApplicationResponseDTO> getApplicantsByJob(Long jobId) {


        List<Application> applications =
                applicationRepository.findByJobId(jobId);


        List<ApplicationResponseDTO> response =
                new ArrayList<>();


        for(Application application : applications) {


            ApplicationResponseDTO dto =
                    new ApplicationResponseDTO();


            dto.setId(application.getId());

            dto.setJobId(
                    application.getJob().getId()
            );


            dto.setCandidateName(
                    application.getCandidate().getFullName()
            );


            dto.setJobTitle(
                    application.getJob().getTitle()
            );


            dto.setStatus(
                    application.getStatus()
            );


            dto.setAppliedDate(
                    application.getAppliedDate()
            );


            response.add(dto);

        }


        return response;
    }
        public List<ApplicationResponseDTO> getApplicationsByCompany(Long companyId){


            List<Application> applications =
                    applicationRepository
                    .findByJobCompanyId(companyId);


            List<ApplicationResponseDTO> response =
                    new ArrayList<>();


            for(Application application : applications){


                ApplicationResponseDTO dto =
                        new ApplicationResponseDTO();


                dto.setId(application.getId());


                dto.setJobId(
                        application.getJob().getId()
                );


                dto.setCandidateName(
                        application.getCandidate().getFullName()
                );


                dto.setJobTitle(
                        application.getJob().getTitle()
                );


                dto.setStatus(
                        application.getStatus()
                );


                dto.setAppliedDate(
                        application.getAppliedDate()
                );


                response.add(dto);

            }


            return response;

        }

    }
