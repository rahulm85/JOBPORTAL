package com.jobportal.entity;
import lombok.Data;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.jobportal.dto.SavedJobRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="saved_jobs")


@Data
public class SavedJob {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@CreationTimestamp
	private LocalDate savedDate;
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private User candidate;

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
}
