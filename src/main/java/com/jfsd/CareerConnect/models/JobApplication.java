package com.jfsd.CareerConnect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.util.Base64;


@Entity
@Table(name = "job_application")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String yearInUniversity;

    @Column(nullable = false)
    private String status = "NotApplied";

    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false)
    private byte[] resume;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false )
    @JsonBackReference  // Prevent infinite recursion by not serializing the job field
    private Job job;

    // Default constructor
    public JobApplication() {}

    // Parameterized constructor
    public JobApplication(int id, String name, String email, String university, String phoneNumber, 
                          String yearInUniversity, String status, byte[] resume, Student student, Job job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.university = university;
        this.phoneNumber = phoneNumber;
        this.yearInUniversity = yearInUniversity;
        this.status = status;
        this.resume = resume;
        this.student = student;
        this.job = job;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYearInUniversity() {
        return yearInUniversity;
    }

    public void setYearInUniversity(String yearInUniversity) {
        this.yearInUniversity = yearInUniversity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
    public String getResumeUrl() {
        return Base64.getEncoder().encodeToString(this.resume);  // Convert byte array to Base64 String
    }

}
