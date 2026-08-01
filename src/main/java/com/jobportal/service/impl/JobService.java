package com.jobportal.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.JobRequestDTO;
import com.jobportal.dto.JobResponseDTO;
import com.jobportal.entity.Company;
import com.jobportal.entity.Job;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;


@Service
public class JobService {


    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private CompanyRepository companyRepository;



    // CREATE JOB

    public String createJob(JobRequestDTO request) {


        Company company =
                companyRepository.findById(request.getCompanyId())
                .orElse(null);



        if(company == null) {

            return "Company Not Found";

        }



        Job job = new Job();


        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setExperience(request.getExperience());
        job.setJobType(request.getJobType());
        job.setCategory(request.getCategory());
        job.setVacancy(request.getVacancy());
        job.setDeadline(request.getDeadline());

        job.setPostedDate(LocalDate.now());

        job.setActive(true);


        // IMPORTANT
        job.setCompany(company);



        jobRepository.save(job);



        return "Job Created Successfully";

    }





    // GET ALL JOBS FOR CANDIDATE

    public List<JobResponseDTO> getAllJobs(){


        List<Job> jobs = jobRepository.findAll();


        List<JobResponseDTO> response = new ArrayList<>();



        for(Job job : jobs){


            JobResponseDTO dto = convertToDTO(job);


            response.add(dto);

        }


        return response;

    }





    // GET JOBS BY COMPANY FOR RECRUITER

    public List<JobResponseDTO> getJobsByCompany(Long companyId){


        List<Job> jobs =
                jobRepository.findByCompanyId(companyId);



        List<JobResponseDTO> response = new ArrayList<>();



        for(Job job : jobs){


            response.add(convertToDTO(job));


        }



        return response;

    }






    // GET JOB BY ID

    public JobResponseDTO getJobById(Long id){


        Job job =
                jobRepository.findById(id)
                .orElse(null);



        if(job == null){

            return null;

        }



        return convertToDTO(job);

    }






    // COMMON DTO CONVERSION

    private JobResponseDTO convertToDTO(Job job){


        JobResponseDTO dto = new JobResponseDTO();



        dto.setId(job.getId());

        dto.setTitle(job.getTitle());

        dto.setDescription(job.getDescription());

        dto.setLocation(job.getLocation());

        dto.setSalary(job.getSalary());

        dto.setExperience(job.getExperience());

        dto.setJobType(job.getJobType());

        dto.setCategory(job.getCategory());

        dto.setVacancy(job.getVacancy());

        dto.setDeadline(job.getDeadline());

        dto.setPostedDate(job.getPostedDate());

        dto.setActive(job.getActive());



        if(job.getCompany()!=null){

            dto.setCompanyName(
                job.getCompany().getCompanyName()
            );

        }
        else{

            dto.setCompanyName("Unknown");

        }



        return dto;

    }






    // UPDATE JOB

    public String updateJob(Long id, JobRequestDTO request){


        Job job =
                jobRepository.findById(id)
                .orElse(null);



        if(job == null){

            return "Job Not Found";

        }



        job.setTitle(request.getTitle());

        job.setDescription(request.getDescription());

        job.setLocation(request.getLocation());

        job.setSalary(request.getSalary());

        job.setExperience(request.getExperience());

        job.setJobType(request.getJobType());

        job.setCategory(request.getCategory());

        job.setVacancy(request.getVacancy());

        job.setDeadline(request.getDeadline());



        jobRepository.save(job);



        return "Job Updated Successfully";

    }







    // DELETE JOB

    public String deleteJob(Long id){


        Job job =
                jobRepository.findById(id)
                .orElse(null);



        if(job == null){

            return "Job Not Found";

        }



        jobRepository.delete(job);



        return "Job Deleted Successfully";

    }






    public List<Job> searchByTitle(String title){

        return jobRepository.findByTitleContainingIgnoreCase(title);

    }



    public List<Job> searchByLocation(String location){

        return jobRepository.findByLocationContainingIgnoreCase(location);

    }



    public List<Job> searchByCategory(String category){

        return jobRepository.findByCategoryContainingIgnoreCase(category);

    }



    public List<Job> searchByJobType(String jobType){

        return jobRepository.findByJobTypeContainingIgnoreCase(jobType);

    }


}