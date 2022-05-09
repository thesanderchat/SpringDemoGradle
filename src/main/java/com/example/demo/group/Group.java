package com.example.demo.group;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )
    private Long id;
    @NotEmpty(message = "Name may not be empty")
    private String name;
    private LocalDate dateOfCreation;
    @JsonManagedReference
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> studentList= new ArrayList<>();

    public void addNewStudentToStudentList(Student student){
        studentList.add(student);
        student.setGroup(this);
    }
    void removeStudentFromStudentList(Student student){
        studentList.remove(student);
        student.setGroup(null);
    }
    public Group() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Group(String name, LocalDate dateOfCreation, List<Student> studentList) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public Group(Long id, String name, LocalDate dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public Group(String name, LocalDate dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public Group(String name) {
        this.name = name;
    }
}