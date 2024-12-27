package com.jfsd.CareerConnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.Recruiter;

public interface JobRepository extends JpaRepository<Job, Integer>{
	
	public List<Job> findByRecruiter(Recruiter recruiter);
	

}
