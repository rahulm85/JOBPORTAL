package com.jobportal.dto;

import lombok.Data;

@Data
public class CompanyRequestDTO {

    private String companyName;
    private String description;
    private String website;
    private String location;

}