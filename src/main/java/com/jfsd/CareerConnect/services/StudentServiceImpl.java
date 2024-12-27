 package com.jfsd.CareerConnect.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.repository.JobApplicationRepository;
import com.jfsd.CareerConnect.repository.JobRepository;
import com.jfsd.CareerConnect.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentrepo;
	
	
	@Autowired
	private JobRepository jobrepo;
	
	@Autowired
	private JobApplicationRepository jobapplicationrepo;

	@Override
	public List<Job> viewalljobs() {
		List<Job> list=jobrepo.findAll();
		return list;
	}

	@Override
	public String application(JobApplication applicationdata, int jobid, int studentid,MultipartFile file) throws IOException {
	    Optional<Job> j = jobrepo.findById(jobid);
	    if (j.isEmpty()) {
	        throw new EntityNotFoundException("Job not found for ID: " + jobid);
	    }
	    Job job = j.get();
	    
	    // Retrieve student entity
	    Optional<Student> s = studentrepo.findById(studentid);
	    if (s.isEmpty()) {
	        throw new EntityNotFoundException("Student not found for ID: " + studentid);
	    }
	    Student student = s.get();
	    
	    byte[] resume=file.getBytes();
	    applicationdata.setStudent(student);
	    applicationdata.setJob(job);
	    applicationdata.setResume(resume);
	    
	    jobapplicationrepo.save(applicationdata);
	    System.out.println("Done!!!..........");
	    return "Application submitted successfully for job: " + job.getTitle();
	}

	
	@Override
	public List<Job> viewJobsById(int studentId) {
	    Optional<Student> studentOptional = studentrepo.findById(studentId);
	    if (studentOptional.isEmpty()) {
	        throw new RuntimeException("Student not found with ID: " + studentId);
	    }

	    Student student = studentOptional.get();
	    List<JobApplication> jobApplications = jobapplicationrepo.findByStudent(student);

	    List<Job> jobList = new ArrayList<>();
	    for (JobApplication application : jobApplications) {
	        Job job = application.getJob();
	        if (job != null) {
	            jobList.add(job);
	        }
	    }

	    return jobList;
	}

	@Override
	public String checkjobapplication(int jobid, int studentid) {
		JobApplication j= jobapplicationrepo.findByStudentAndJob(studentid, jobid);
		if(j==null)
		{
			return "Job not Applied";
		}
		return "Job Applied";
	}
	
	@Override
	public Student viewStudentById(int id) {
	    Optional<Student> studentdata = studentrepo.findById(id);
	    if (studentdata.isPresent()) {
	        Student student = studentdata.get();
	        if (student.getPhoto() != null) {
	            // Convert the byte[] photo to Base64 string
	            String base64Photo = Base64.getEncoder().encodeToString(student.getPhoto());
	            // Set Base64 photo as a String in the Student object
	            student.setPhotoBase64(base64Photo);
	        }
	        return student;
	    } else {
	        throw new EntityNotFoundException("Student with ID " + id + " not found.");
	    }
	}


	
	public String updateStudent(Student s, MultipartFile photo) throws IOException {
	    Optional<Student> studentOptional = studentrepo.findById(s.getId());
	    
	    if (studentOptional.isEmpty()) {
	        throw new EntityNotFoundException("Student not found");
	    }

	    Student student = studentOptional.get();
	    // Update the fields
	    student.setName(s.getName());
	    student.setCgpa(s.getCgpa());
	    student.setPhoneNumber(s.getPhoneNumber());
	    student.setProgram(s.getProgram());
	    student.setEmail(s.getEmail());
	    student.setDepartment(s.getBranch());
	    student.setGraduationYear(s.getGraduationYear());
	    student.setUsername(s.getUsername());
	    student.setPassword(s.getPassword());

	    // Handle photo upload
	    if (photo != null && !photo.isEmpty()) {
	        student.setPhoto(photo.getBytes());
	    }

	    studentrepo.save(student);

	    return "Student profile updated successfully!";
	}


		
}

