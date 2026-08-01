package com.jobportal.dto;

import lombok.Data;

@Data
public class DashboardResponseDTO {

    private String name;
    private Integer totalJobs;
    private Integer totalApplications;
    private Integer totalSavedJobs;

}