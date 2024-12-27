package com.jfsd.CareerConnect.models;

import java.util.Base64;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;

@Entity
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recruiterId;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contact_number",unique = true,nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String contactNumber;

    @Column(name = "industry",nullable = false)
    private String industry;

    @Column(name = "location",nullable = false)
    private String location;
    
    @Column(name="websiteurl",nullable = false)
    private String websiteurl;
    
    @Column(name="username",unique = true,nullable = false)
    private String username;
    
    @Column(name="password",nullable = false)
    private String password;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;
    
    private String photoBase64;

    
    public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private List<Job> jobs;
    
	public int getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getWebsiteurl() {
		return websiteurl;
	}

	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}
	
	public Recruiter()
	{
		
	}
	public Recruiter(byte[] photo)
	{
		this.photo=photo;
	}

	public Recruiter(int recruiterId, String name, String company, String email, String contactNumber, String industry,
			String location, String websiteurl, String username, String password) {
		this.recruiterId = recruiterId;
		this.name = name;
		this.company = company;
		this.email = email;
		this.contactNumber = contactNumber;
		this.industry = industry;
		this.location = location;
		this.websiteurl = websiteurl;
		this.username = username;
		this.password = password;
	}
	
	
	public String getPhotoBase64() {
	    return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
	}

	public void setPhotoBase64(String photoBase64) {
	    this.photoBase64 = photoBase64;
	}

	
	
}