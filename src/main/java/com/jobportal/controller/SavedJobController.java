package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.SavedJobRequestDTO;
import com.jobportal.dto.SavedJobResponseDTO;
import com.jobportal.service.impl.SavedJobService;

@RestController
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    // Save Job
    @PostMapping("/saved-job")
    public String saveJob(@RequestBody SavedJobRequestDTO request) {

        return savedJobService.saveJob(request);
    }

    // Get Saved Jobs
    @GetMapping("/saved-jobs/{candidateId}")
    public List<SavedJobResponseDTO> getSavedJobs(@PathVariable Long candidateId) {

        return savedJobService.getSavedJobs(candidateId);
    }

    // Remove Saved Job
    @DeleteMapping("/saved-job/{id}")
    public String removeSavedJob(@PathVariable Long id) {

        return savedJobService.removeSavedJob(id);
    }
}