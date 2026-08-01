package com.jobportal.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name="Job")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message="Title of job is needed")
    @Column(nullable=false)
    private String title;


    @Lob
    @NotBlank(message="Description of job is needed")
    @Column(nullable=false)
    private String description;


    @NotBlank(message="Location is needed")
    @Column(nullable=false)
    private String location;


    @NotNull(message="Salary Cannot be Null")
    @Positive(message="Salary must be greater than 0")
    @Column(nullable=false)
    private Double salary;


    @Column
    private Integer experience;


    @NotBlank(message="Specify Role")
    @Column
    private String jobType;


    @Column
    private String category;


    @Column
    private Integer vacancy;


    @Column
    private LocalDate deadline;


    @Column
    private LocalDate postedDate;


    @Column
    private Boolean active;


    // CHANGED HERE
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;



    @OneToMany(mappedBy = "job")
    private List<Application> applications;


    @OneToMany(mappedBy = "job")
    private List<SavedJob> savedJobs;

}