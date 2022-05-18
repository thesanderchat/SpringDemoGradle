package com.example.demo.group;

import com.example.demo.student.StudentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupDto {
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotNull(message = "Date of creation may not be null")
    @Column(nullable = false)
    private LocalDate dateOfCreation;
    private List<StudentDto> studentList = new ArrayList<>();

    public GroupDto(@NonNull String name, @NonNull LocalDate dateOfCreation, List<StudentDto> studentList) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public GroupDto(@NonNull String name, @NonNull LocalDate dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public GroupDto(Long id, String name, LocalDate dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public GroupDto(Long id, String name, LocalDate dateOfCreation, List<StudentDto> studentList) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public void addNewListOfStudentsToStudentList(List<StudentDto> students) {
        for (StudentDto student : students
        ) {
            studentList.add(student);
            student.setGroupId(this.getId());
        }
    }

}
