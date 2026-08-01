package com.jobportal.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class JobRequestDTO {
	private Long companyId;
    private String title;
    private String description;
    private String location;
    private Double salary;
    private Integer experience;
    private String jobType;
    private String category;
    private Integer vacancy;
    private LocalDate deadline;

}