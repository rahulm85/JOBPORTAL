package com.jobportal.dto;

import lombok.Data;

@Data
public class CompanyResponseDTO {

    private Long id;
    private String companyName;
    private String description;
    private String website;
    private String location;

}