package com.jobportal.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@Entity
@Table(name="Companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name cannot be blank")
    @Column(nullable = false, unique = true)
    private String companyName;

    @NotBlank(message = "Description Needed")
    @Column(nullable = false)
    private String description;

    @Column(unique = true)
    private String website;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @Column
    private String logo;
    
    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;
    @OneToMany(mappedBy = "company")
    private List<Job> jobs;
}