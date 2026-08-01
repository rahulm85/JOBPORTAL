package com.jobportal.entity;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name="Applications")
@Data
public class Application {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String resumePath;
	@NotBlank
	@Column(nullable=false)
	private String status;
	@CreationTimestamp
	private LocalDate appliedDate;
	@ManyToOne
	@JoinColumn(name="candidate_id")
	private User candidate;
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;
	@Column(length = 1000)
	private String coverLetter;
	    // Add manually
	    public void setCoverLetter(String coverLetter) {
	        this.coverLetter = coverLetter;
	    }

	}

