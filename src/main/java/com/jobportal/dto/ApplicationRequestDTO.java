package com.jobportal.dto;

import lombok.Data;

@Data
public class ApplicationRequestDTO {

    private Long candidateId;

    private Long jobId;

    private String coverLetter;

    private String resumePath;

}