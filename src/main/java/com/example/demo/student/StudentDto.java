package com.example.demo.student;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class StudentDto {
    @NonNull
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NonNull
    @NotEmpty(message = "Email may not be empty")
    private String email;
    @NonNull
    private LocalDate dateOfBirth;
    private Long groupId;


    public StudentDto(@NonNull String name, @NonNull String email, @NonNull LocalDate dateOfBirth, Long groupId) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.groupId = groupId;
    }

    public StudentDto(@NonNull String name, @NonNull String email, @NonNull LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public StudentDto() {
    }
}
