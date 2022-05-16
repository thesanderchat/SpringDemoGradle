package com.example.demo.student;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    @Column(nullable = false)
    private Long id;
    @NotEmpty(message = "Name may not be empty")
    @Column(nullable = false)
    private String name;
    @NotEmpty(message = "Email may not be empty")
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
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

}
