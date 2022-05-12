package com.example.demo.group;

import com.example.demo.student.Student;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GroupDto {
    @NotEmpty(message = "Name may not be empty")
    private String name;
    private LocalDate dateOfCreation;
    private List<Student> studentList = new ArrayList<>();

    public GroupDto() {
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public GroupDto(String name, LocalDate dateOfCreation, List<Student> studentList) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public GroupDto(String name, LocalDate dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }
}
