package com.example.demo.group;

import com.example.demo.student.Student;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupDto {
    @NonNull
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NonNull
    private LocalDate dateOfCreation;
    private List<Student> studentList = new ArrayList<>();

    public GroupDto(@NonNull String name, @NonNull LocalDate dateOfCreation, List<Student> studentList) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public GroupDto(@NonNull String name, @NonNull LocalDate dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }
}
