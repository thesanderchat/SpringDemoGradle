package com.example.demo.student;


import com.example.demo.group.Group;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class StudentDto {
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotEmpty(message = "Email may not be empty")
    private String email;
    private LocalDate dateOfBirth;
    private Group group;

    public StudentDto(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public StudentDto(String name, String email, LocalDate dateOfBirth, Group group) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
