	package com.jfsd.CareerConnect.models;
	
	import jakarta.persistence.*;
	import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
	
	@Entity
	@Table(name = "students")
	public class Student {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    private int id;
	
	    @Column(nullable = false)
	    private String name;
	    
	    @Column
	    private LocalDate dateOfBirth;
	    
	    @Column(name = "gender")
	    private String gender;
	
	
	    @Column(name = "phone_number", nullable = false,unique = true)
	    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
	    private String phoneNumber;
	
	    @Column(nullable = false, unique = true)
	    //@Email(message = "Invalid email format")
	    private String email;
	    
	    @Column(nullable = false)
	    private String program;
	
	    @Column(nullable = false)
	    private String branch;
	    
	    @Column(name = "university_name")
	    private String universityName;
	    
	    @Column(name = "cgpa", nullable = false)
	    private Double cgpa;
	
	    @Column(name = "graduation_year")
	    private int graduationYear;
	    
	    @Column(name = "skills")
	    private String skills;
	    
	    @Column(name = "certifications")
	    private String certifications;
	    
	    @Column(name = "Experience")
	    private String experience;
	    
	    @Lob
	    @Column(name = "resume")
	    private byte[] resume;  // This could be an InputStream if you prefer to load from file directly

	    
	    @Column(name = "username",unique=true,nullable=false)
	    private String username;
	    
	    @Column(name = "password",nullable=false)
	    private String password;
	    
	    @Lob
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] photo;
	    
	    private String photoBase64;
	    
	    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	    private List<JobApplication> applications;
	
	
	    // Default constructor
	    public Student( ) {}
	    
	    public Student(byte[] photo) {
	    	this.photo=photo;
	    }
	
	    public Student(String name,LocalDate dateofbirth, double cgpa, String phoneNumber, String program, String email,
	                   String branch, int graduationYear, String username,String password) {
	        this.name = name;
	        this.dateOfBirth=dateofbirth;
	        this.cgpa = cgpa;
	        this.phoneNumber = phoneNumber;
	        this.program = program;
	        this.email = email;
	        this.branch = branch;
	        this.graduationYear = graduationYear;
	        this.username=username;
	        this.password=password;
	    }
	
	    // Getters and Setters
	    public int getId() {
	        return id;
	    }
	
	    public void setId(int id) {
	        this.id = id;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public double getCgpa() {
	        return cgpa;
	    }
	
	    public void setCgpa(double cgpa) {
	        this.cgpa = cgpa;
	    }
	
	    public String getPhoneNumber() {
	        return phoneNumber;
	    }
	
	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }
	
	    public String getProgram() {
	        return program;
	    }
	
	    public void setProgram(String program) {
	        this.program = program;
	    }
	
	    public String getEmail() {
	        return email;
	    }
	
	    public void setEmail(String email) {
	        this.email = email;	
	    }
	
	    public String getBranch() {
	        return branch;
	    }
	
	    public void setDepartment(String branch) {
	        this.branch = branch;
	    }
	
	    public int getGraduationYear() {
	        return graduationYear;
	    }
	
	    public void setGraduationYear(int graduationYear) {
	        this.graduationYear = graduationYear;
	    }
	    
	    public byte[] getResume() {
	        return resume;
	    }

	    public void setResume(byte[] resume) {
	        this.resume = resume;
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
	
	    // String representation
	    @Override
	    public String toString() {
	        return "Student{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", cgpa=" + cgpa +
	                ", phoneNumber='" + phoneNumber + '\'' +
	                ", program='" + program + '\'' +
	                ", email='" + email + '\'' +
	                ", department='" + branch + '\'' +
	                ", graduationYear=" + graduationYear +
	                '}';
	    }
	
		public byte[] getPhoto() {
			return photo;
		}
	
		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}

		public String getPhotoBase64() {
		    return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
		}

		public void setPhotoBase64(String photoBase64) {
		    this.photoBase64 = photoBase64;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getUniversityName() {
			return universityName;
		}

		public void setUniversityName(String universityName) {
			this.universityName = universityName;
		}

		public String getSkills() {
			return skills;
		}

		public void setSkills(String skills) {
			this.skills = skills;
		}

		public String getCertifications() {
			return certifications;
		}

		public void setCertifications(String certifications) {
			this.certifications = certifications;
		}

		public String getExperience() {
			return experience;
		}

		public void setExperience(String experience) {
			this.experience = experience;
		}

		public Student(String name, LocalDate dateOfBirth, String gender,
				@Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number") String phoneNumber, String email,
				String program, String branch, String universityName, Double cgpa, int graduationYear, String skills,
				String certifications, String experience, byte[] resume, String username, String password,
				byte[] photo) {
			super();
			this.name = name;
			this.dateOfBirth = dateOfBirth;
			this.gender = gender;
			this.phoneNumber = phoneNumber;
			this.email = email;
			this.program = program;
			this.branch = branch;
			this.universityName = universityName;
			this.cgpa = cgpa;
			this.graduationYear = graduationYear;
			this.skills = skills;
			this.certifications = certifications;
			this.experience = experience;
			this.resume = resume;
			this.username = username;
			this.password = password;
			this.photo = photo;
		}
	
		
	}
