package com.jobportal.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ApplicationResponseDTO {

    private Long id;

    private Long jobId;

    private String candidateName;

    private String jobTitle;

    private String status;

    private LocalDate appliedDate;

}