package com.jfsd.CareerConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.services.RecruiterService;

@RestController
@RequestMapping("recruiter")
@CrossOrigin
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    // Fetch recruiter details by recruiterId
    @GetMapping("/viewbyid/{id}")
    public ResponseEntity<?> viewById(@PathVariable("id") int id) {
        try {
            Recruiter recruiter = recruiterService.getRecruiterById(id);
            if (recruiter != null) {
                return ResponseEntity.ok(recruiter);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recruiter not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/updaterecruiter")
    public ResponseEntity<String> updateRecruiter(
        @RequestPart("recruiter") String recruiterJson,
        @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Recruiter recruiter = objectMapper.readValue(recruiterJson, Recruiter.class);

            String result = recruiterService.updateRecruiter(recruiter, photo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    
    
    @PostMapping("addjob")
    public ResponseEntity<Job> insertJob(@RequestBody Job job,@RequestParam int recruiterId)
    {
    	String s= recruiterService.addJob(job,recruiterId);
    	if(s.contains("successfully"))
    	{
    		return new ResponseEntity<>(job,HttpStatus.OK);
    	}
    	else
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    
    @GetMapping("viewalljobs")
    public ResponseEntity<List<Job>> viewalljobs(@RequestParam("id") int recruiterId) {
        List<Job> list = recruiterService.viewalljobs(recruiterId);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Empty list
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("viewapplicationsbyid")
    public ResponseEntity<?> viewApplicationsById(@RequestParam("jobId") int jobId) {
        try {
            List<JobApplication> applications = recruiterService.viewapplicationsbyjobid(jobId);

            if (applications.isEmpty()) {
                return new ResponseEntity<>("No applications found for the given job ID.", HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
