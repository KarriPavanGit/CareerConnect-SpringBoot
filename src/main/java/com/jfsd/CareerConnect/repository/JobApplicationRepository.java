package com.jfsd.CareerConnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Student;


public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
	List<JobApplication> findByStudent(Student student);
	
	@Query("SELECT ja FROM JobApplication ja WHERE ja.student.id = :studentId AND ja.job.id = :jobId")
	JobApplication findByStudentAndJob(@Param("studentId") int studentId, @Param("jobId") int jobId);
	
	
	public List<JobApplication> findByJob(Job job);
}
