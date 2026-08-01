package com.jobportal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class JobResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Double salary;
    private Integer experience;
    private String jobType;
    private String category;
    private Integer vacancy;
    private LocalDate deadline;
    private LocalDate postedDate;
    private Boolean active;
    private String companyName;

}