package com.jfsd.CareerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jfsd.CareerConnect.models.Admin;
import com.jfsd.CareerConnect.models.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{

//	@Query("SELECT a FROM Student a WHERE a.email = ?1 AND a.password = ?2")
//	public Student checkstudentlogin(String username,String password);
	
	public Student findByUsernameAndPassword(String username,String Password);
	

	public boolean existsByUsername(String username);


	public boolean existsByEmail(String email);


	public boolean existsByPhoneNumber(String phoneNumber);


	public Student findByUsername(String username);
}
