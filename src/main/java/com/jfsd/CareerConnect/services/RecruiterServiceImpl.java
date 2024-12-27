package com.jfsd.CareerConnect.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.repository.JobApplicationRepository;
import com.jfsd.CareerConnect.repository.JobRepository;
import com.jfsd.CareerConnect.repository.RecruiterRepository;
import com.jfsd.CareerConnect.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@Service
public class RecruiterServiceImpl implements RecruiterService{

	@Autowired
    private RecruiterRepository recruiterrepo;
	
	@Autowired
	private JobRepository jobrepo;
	
	@Autowired
	private JobApplicationRepository jobapplicationrepo;
	
	@Autowired
	private  StudentRepository studentrepo;
	
	 @Autowired
	 private JavaMailSender mailSender;

	 @Override
	 public Recruiter getRecruiterById(int recruiterId) {
	     Optional<Recruiter> recruiterdata= recruiterrepo.findById(recruiterId);
	     if(recruiterdata.isPresent())
	     {
	    	 Recruiter recruiter=recruiterdata.get();
	    	 if(recruiter.getPhoto()!=null)
	    	 {
	    		 String base64photo=Base64.getEncoder().encodeToString(recruiter.getPhoto());
	    		 recruiter.setPhotoBase64(base64photo);
	    	 }
	    	 return recruiter;
	     }
	     else
	     {
	    	 throw new EntityNotFoundException("Recruiter with Id "+recruiterId+"Not Found.");
	     }
	 }

	 public String updateRecruiter(Recruiter r, MultipartFile photo) throws IOException {
		    Optional<Recruiter> recruiterOptional = recruiterrepo.findById(r.getRecruiterId());

		    if (recruiterOptional.isEmpty()) {
		        throw new EntityNotFoundException("Recruiter not found");
		    }

		    Recruiter recruiter = recruiterOptional.get();

		    // Update fields
		    recruiter.setName(r.getName());
		    recruiter.setCompany(r.getCompany());
		    recruiter.setEmail(r.getEmail());
		    recruiter.setContactNumber(r.getContactNumber());
		    recruiter.setIndustry(r.getIndustry());
		    recruiter.setLocation(r.getLocation());
		    recruiter.setWebsiteurl(r.getWebsiteurl());
		    recruiter.setUsername(r.getUsername());
		    recruiter.setPassword(r.getPassword());

		    // Handle photo upload
		    if (photo != null && !photo.isEmpty()) {
		        recruiter.setPhoto(photo.getBytes());
		    }

		    recruiterrepo.save(recruiter);

		    return "Recruiter profile updated successfully!";
		}


	@Override
	public String addJob(Job job, int recruiterId) {
        String out = "";
        Optional<Recruiter> recruiteroptional = recruiterrepo.findById(recruiterId);
        if (recruiteroptional.isEmpty()) {
            throw new RuntimeException("Recruiter not found with id: " + recruiterId);
        }
        Recruiter recruiter = recruiteroptional.get();
        try {
            job.setRecruiter(recruiter);
            jobrepo.save(job);
            System.out.println("Job Added..");

            // Fetch all students
            List<Student> students = studentrepo.findAll();
            if (!students.isEmpty()) {
                for (Student student : students) {
                    sendJobEmail(student.getEmail(), job);
                    System.out.println("Email Sent..");
                }
            }

            out = "Job inserted successfully and emails sent to students.";
        } catch (Exception e) {
            out = "Error: " + e.getMessage();
        }
        return out;
    }

	@Async
	private void sendJobEmail(String toEmail, Job job) {
	    try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("pavankarri006@gmail.com");
	        message.setTo(toEmail);
	        message.setSubject("New Job Opportunity: " + job.getTitle());
	        message.setText("Dear Student,\n\nA new job opportunity has been posted:\n\n" +
	                "Job Title: " + job.getTitle() + "\n" +
	                "Location: " + job.getLocation() + "\n" +
	                "Description: " + job.getDescription() + "\n\n" +
	                "Apply now on the portal.\n\nBest Regards,\nCareerConnect Team");

	        mailSender.send(message);
	    } catch (Exception e) {
	        System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
	    }
	}


	@Override
	public String deleteJob(int id) {
		String out="";
		try {
		 jobrepo.deleteById(id);
		 out="Job with id "+id+" deleted successfully";
		}
		catch(Exception e)
		{
			out="Error: "+e.getMessage();
		}
		return out;
	}
	
	public List<Job> viewalljobs(int recruiterId)
	{
		Optional<Recruiter> recruiteroptional=recruiterrepo.findById(recruiterId);
	   if(recruiteroptional.isEmpty()) {
           throw new RuntimeException("Recruiter not found with id: " + recruiterId);
	   }
	   Recruiter recruiter =recruiteroptional.get();
	   
	   return jobrepo.findByRecruiter(recruiter);
	
	}

	@Override
	public List<JobApplication> viewapplicationsbyjobid(int jobId) {
		Optional<Job> j= jobrepo.findById(jobId);
		if(j.isEmpty())
		{
			throw new RuntimeException("Job Not Found with id:"+jobId);
		}
		 List<JobApplication> joblist=jobapplicationrepo.findByJob(j.get());
		return joblist;	
	}

	
	
	
    
}
