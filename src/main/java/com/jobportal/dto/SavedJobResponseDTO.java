package com.jobportal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SavedJobResponseDTO {

    private Long id;
    private String candidateName;
    private String jobTitle;
    private LocalDate savedDate;

}