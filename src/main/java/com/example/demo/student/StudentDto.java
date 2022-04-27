package com.example.demo.student;

import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class StudentDto {
    @NotEmpty(message = "Email may not be empty")
    private String name;
    @NaturalId
    @NotEmpty(message = "Email may not be empty")
    private String email;
    private LocalDate dateOfBirth;

    public StudentDto(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}