package com.jfsd.CareerConnect.controller;
//wsdo tsdt rsca gebg
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.jfsd.CareerConnect.models.Admin;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.services.AdminService;
import com.jfsd.CareerConnect.services.StudentService;

@Controller
@CrossOrigin
@RequestMapping("admin")
public class AdminController
{
	

	@Autowired
    private AdminService adminservice;
	
    
    @PostMapping("/addstudent")
    public ResponseEntity<String> addStudent(
        @RequestParam("name") String name,
        @RequestParam("dateofbirth") LocalDate dob,
        @RequestParam("cgpa") double cgpa,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("program") String program,
        @RequestParam("email") String email,
        @RequestParam("branch") String branch,
        @RequestParam("graduationYear") int graduationYear,
        @RequestParam("username") String username,
        @RequestParam("password") String password) throws Exception {
        
        Student student = new Student(name,dob, cgpa, phoneNumber, program, email, branch,
		                              graduationYear, username, password);
		
		
		String s=adminservice.insertStudent(student);
		
		return ResponseEntity.ok(s);
    }		

	
	@DeleteMapping("deletestudent/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
	    return adminservice.deleteStudent(id);
	}

	
	@PutMapping("/updatestudent")
	public ResponseEntity<String> updateStudent(@RequestBody Student s) {
	    String result = adminservice.updateStudent(s);
	    if (result.contains("successfully")) {
	        return ResponseEntity.ok(result); // 200 OK
	    } else if (result.contains("not found")) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 404 Not Found
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result); // 500 Internal Server Error
	    }
	}

	@GetMapping("viewallstudents")
	public ResponseEntity<List<Student>> viewallstudent()
	{
		return adminservice.viewAllStudents();
	}
	
	@GetMapping("viewbyid/{id}")
	public ResponseEntity<Student> viewbyid(@PathVariable("id") int id)
	{
		return adminservice.viewStudentById(id);
	}
	
		
	
	
// recruiter----------------------------------------------------
	
	
	@PostMapping("addrecruiter")
	public ResponseEntity<String> addrecruiter(@RequestBody Recruiter recruiter)
	{
		try
		{
			String out=adminservice.insertRecruiter(recruiter);
	        return ResponseEntity.ok(out);
			
		}
		catch(Exception e)
		{
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in recruiter insertion"+e.getMessage());
		}
		
	}
	
	@DeleteMapping("deleterecruiter/{id}")
    public ResponseEntity<String> deleteRecruiter(@PathVariable int id) {
        try {
            String result = adminservice.deleteRecruiter(id); 
            if (result.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); 
            } else {
                return ResponseEntity.ok(result); 
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in recruiter deletion: " + e.getMessage());
        }
    }
	
	@GetMapping("viewallrecruiters")	
	public ResponseEntity<?> viewAllRecruiters() {
	    try {
	        List<Recruiter> recruiters = adminservice.viewAllRecruiters();
	        
	        if (recruiters == null || recruiters.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No recruiters found");
	        }
	        return ResponseEntity.ok(recruiters);
	        
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: " + e.getMessage());
	    }
	}
	
	@PutMapping("/updaterecruiter")
	public ResponseEntity<String> updaterecruiter(@RequestBody Recruiter r) {
	    String result = adminservice.updateRecruiter(r);
	    if (result.contains("successfully")) {
	        return ResponseEntity.ok(result); // 200 OK
	    } else if (result.contains("not found")) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 404 Not Found
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result); // 500 Internal Server Error
	    }
	}

}
	
	
	
	
	
//	@Autowired
//	private AdminService adminservice;
//	@RequestMapping("/addadmin")
//	public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
//	    try {
//	        String result = adminservice.addAdmin(admin);
//	        return ResponseEntity.status(HttpStatus.CREATED).body(result);
//	    } catch (ResponseStatusException e) {
//	        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                             .body("An unexpected error occurred: " + e.getMessage());
//	    }
//	}
//
//	
//	 @DeleteMapping("/deleteadmin")
//	    public ResponseEntity<?> deleteAdmin(@RequestParam("id") int id) {
//	        try {
//	            String result = adminservice.deleteAdmin(id);
//	            return ResponseEntity.ok(result);
//	        } catch (ResponseStatusException e) {
//	            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                                 .body("An unexpected error occurred: " + e.getMessage());
//	        }
//	    }
//	
//	
//	 @PutMapping("/updateadmin")
//	    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin) {
//	        try {
//	            String result = adminservice.updateAdmin(admin);
//	            return ResponseEntity.ok(result);
//	        } catch (ResponseStatusException e) {
//	            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                                 .body("An unexpected error occurred: " + e.getMessage());
//	        }
//	    }
//	 
//	 @GetMapping("/viewbyid") 
//	 public ResponseEntity<?> viewadminbyid(@RequestParam("id") int id) {
//	     try {
//	         ResponseEntity<Admin> adata = adminservice.viewadminbyid(id);
//	         return adata; // Directly return the response from the service
//	     } catch (ResponseStatusException e) {
//	         return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
//	     } catch (Exception e) {
//	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                              .body("An unexpected error occurred: " + e.getMessage());
//	     } 
//	 }
//	 
//	 @GetMapping("/viewalladmins") 
//	 public ResponseEntity<?> viewalladmin() {
//	     try {
//	         ResponseEntity<List<Admin>> adminlist = adminservice.viewalladmins();
//	         return adminlist;
//	     } catch (ResponseStatusException e) {
//	         return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
//	     } catch (Exception e) {
//	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                              .body("An unexpected error occurred: " + e.getMessage());
//	     } 
//	 }
//	 
	 

