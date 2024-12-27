package com.jfsd.CareerConnect.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.CareerConnect.models.Admin;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.repository.AdminRepository;
import com.jfsd.CareerConnect.repository.RecruiterRepository;
import com.jfsd.CareerConnect.repository.StudentRepository;

@Controller
@CrossOrigin
public class HomeController {

    @Autowired
    private AdminRepository adminrepo;

    @Autowired
    private StudentRepository studentrepo;
    
    @Autowired
    private RecruiterRepository recruiterrepo;
    

    @GetMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        Admin admin = adminrepo.findByUsernameAndPassword(username, password);
        if (admin != null) {
        	Map<String, Object> response = new HashMap<>();
            response.put("id", admin.getId());
            response.put("role", "admin");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        Student student = studentrepo.findByUsernameAndPassword(username, password);
        if (student != null) {
        	Map<String, Object> response = new HashMap<>();
            response.put("id", student.getId());
            response.put("role", "student");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Recruiter recruiter=recruiterrepo.findByUsernameAndPassword(username, password);
        if(recruiter!=null)
        {
        	Map<String, Object> response = new HashMap<>();
            response.put("id", recruiter.getRecruiterId());
            response.put("role", "recruiter");
            response.put("company",recruiter.getCompany());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials",HttpStatus.UNAUTHORIZED);  // Return 401 for invalid login
    
    }
}


//          spring.datasource.url=jdbc:mysql://root:bGAQiHZnZEjaLttePMPPlvwWeKbXvYoG@autorack.proxy.rlwy.net:30873/railway
//        	spring.datasource.username=root
//        	spring.datasource.password=bGAQiHZnZEjaLttePMPPlvwWeKbXvYoG