package com.jfsd.CareerConnect.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jfsd.CareerConnect.models.Admin;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;

public interface AdminService {
	
//	public String addAdmin(Admin admin);
//	public String deleteAdmin(int id);
//	public String updateAdmin(Admin admin);
//	public ResponseEntity<Admin> viewadminbyid(int id);
//	public ResponseEntity<List<Admin>> viewalladmins();
	
	public String insertStudent(Student st);
	public String deleteStudent(int id);
	public String updateStudent(Student st);
	public ResponseEntity<Student> viewStudentById(int id);
	public ResponseEntity<List<Student>> viewAllStudents();
	
	
	public String insertRecruiter(Recruiter recruiter);
    public String deleteRecruiter(int id);
    public String updateRecruiter(Recruiter recruiter);
    public ResponseEntity<Recruiter> viewRecruiterById(int id);
    public List<Recruiter> viewAllRecruiters();

}
