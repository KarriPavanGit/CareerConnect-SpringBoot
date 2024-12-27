package com.jfsd.CareerConnect.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Student;

public interface StudentService {
	
	
	public List<Job> viewalljobs();
	
	public String application(JobApplication applicationdata,int jodid,int studentid,MultipartFile resume) throws IOException;
	
	public List<Job> viewJobsById(int studentId);
	
	public String checkjobapplication(int jobid,int studentid);

	public Student viewStudentById(int id);
	
	public String updateStudent(Student student, MultipartFile photo) throws IOException;



}
