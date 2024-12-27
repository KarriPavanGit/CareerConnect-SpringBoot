package com.jfsd.CareerConnect.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.services.AdminService;
import com.jfsd.CareerConnect.services.StudentService;

@RestController
@CrossOrigin
@RequestMapping("student")
public class StudentController {
     
    @Autowired
    private StudentService studentservice;
    	
    @GetMapping("/viewalljobs")
    public List<Job> jobs()
    {
    	List<Job> list=studentservice.viewalljobs();
    	return list;
    }
    
    @PostMapping("apply")
    public ResponseEntity<?> applyForJob(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("university") String university,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("yearInUniversity") String yearInUniversity,
        @RequestParam("resume") MultipartFile resume,
        @RequestParam("jobId") int jobId,
        @RequestParam("studentId") int studentId) throws IOException {

        if (resume.isEmpty()) {
            return ResponseEntity.badRequest().body("Resume file is required");
        }

        try {
            // Prepare JobApplication object
            JobApplication jobApplication = new JobApplication();
            jobApplication.setName(name);
            jobApplication.setEmail(email);
            jobApplication.setUniversity(university);
            jobApplication.setPhoneNumber(phoneNumber);
            jobApplication.setYearInUniversity(yearInUniversity);
            jobApplication.setResume(resume.getBytes());

            // Call the service to handle the application
            String responseMessage = studentservice.application(jobApplication, jobId, studentId, resume);
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while processing your application");
        }
    }

    
    @GetMapping("viewjobsbyid")
    public ResponseEntity<?> viewJobsById(@RequestParam("studentId") int studentId) {
        try {
            List<Job> list = studentservice.viewJobsById(studentId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching jobs.");
        }
    }

    
    @PostMapping("checkjobapplication")
    public ResponseEntity<?> checkjobapplication(@RequestParam("jobid") int jobid, @RequestParam("studentid") int studentid) {
        String result = studentservice.checkjobapplication(jobid, studentid);
        
        if(result.contains("not")) {
            return ResponseEntity.status(HttpStatus.OK).body("Job not applied");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Job applied");
        }
    }
    
    @GetMapping("viewbyid/{id}")
    public ResponseEntity<?> viewbyid(@PathVariable("id") int id) {
        try {
            Student student = studentservice.viewStudentById(id); // Change this based on actual implementation
            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    
    @PostMapping("/updatestudent")
    public ResponseEntity<String> updateStudent(
        @RequestPart("student") String studentJson,
        @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(studentJson, Student.class);

            String result = studentservice.updateStudent(student, photo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    
    
    
}
