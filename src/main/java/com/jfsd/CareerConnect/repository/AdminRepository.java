package com.jfsd.CareerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jfsd.CareerConnect.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
//	@Query("SELECT a FROM Admin a WHERE a.id = ?1 AND a.password = ?2")
//	public Admin checkadminlogin(String username,String password);
	
	public Admin findByUsernameAndPassword(String username,String password);
	
	public boolean existsByUsername(String username);
	

}
