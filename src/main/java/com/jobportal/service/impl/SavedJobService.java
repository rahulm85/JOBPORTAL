package com.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.dto.SavedJobRequestDTO;
import com.jobportal.dto.SavedJobResponseDTO;
import com.jobportal.entity.Job;
import com.jobportal.entity.SavedJob;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.SavedJobRepository;
import com.jobportal.repository.UserRepository;


@Service
public class SavedJobService {


    @Autowired
    private SavedJobRepository savedJobRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JobRepository jobRepository;



    // SAVE JOB
    public String saveJob(SavedJobRequestDTO request) {


        // Check duplicate save
        boolean exists =
                savedJobRepository.existsByCandidateIdAndJobId(
                        request.getCandidateId(),
                        request.getJobId()
                );


        if (exists) {

            return "Job Already Saved";

        }



        User candidate =
                userRepository.findById(request.getCandidateId())
                .orElse(null);



        if(candidate == null) {

            return "Candidate Not Found";

        }




        Job job =
                jobRepository.findById(request.getJobId())
                .orElse(null);



        if(job == null) {

            return "Job Not Found";

        }




        SavedJob savedJob = new SavedJob();


        savedJob.setCandidate(candidate);

        savedJob.setJob(job);



        savedJobRepository.save(savedJob);



        return "Job Saved Successfully";

    }





    // GET SAVED JOBS OF CANDIDATE
    public List<SavedJobResponseDTO> getSavedJobs(Long candidateId) {



        List<SavedJob> savedJobs =
                savedJobRepository.findByCandidateId(candidateId);



        List<SavedJobResponseDTO> response =
                new ArrayList<>();



        for(SavedJob savedJob : savedJobs) {



            SavedJobResponseDTO dto =
                    new SavedJobResponseDTO();



            dto.setId(savedJob.getId());


            dto.setCandidateName(
                    savedJob.getCandidate().getFullName()
            );


            dto.setJobTitle(
                    savedJob.getJob().getTitle()
            );


            dto.setSavedDate(
                    savedJob.getSavedDate()
            );



            response.add(dto);

        }



        return response;

    }





    // REMOVE SAVED JOB
    public String removeSavedJob(Long id) {



        SavedJob savedJob =
                savedJobRepository.findById(id)
                .orElse(null);



        if(savedJob == null) {

            return "Saved Job Not Found";

        }



        savedJobRepository.delete(savedJob);



        return "Saved Job Removed Successfully";

    }

}