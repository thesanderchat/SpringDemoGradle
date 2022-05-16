package com.example.demo.group;

import com.example.demo.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotNull(message = "Date of creation may not be null")
    @Column(nullable = false)
    private LocalDate dateOfCreation;
    private List<Student> studentList;

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
