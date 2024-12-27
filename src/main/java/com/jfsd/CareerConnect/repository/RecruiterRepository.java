package com.jfsd.CareerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfsd.CareerConnect.models.Recruiter;
import java.util.List;


@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Integer>{

	public Recruiter findByUsernameAndPassword(String username, String password);
	
	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public boolean existsByContactNumber(String contactNumber);
}
