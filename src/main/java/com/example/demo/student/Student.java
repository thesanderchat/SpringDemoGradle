package com.example.demo.student;

import com.example.demo.group.Group;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "students")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(nullable = false)
    private Long id;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NonNull
    @Column(nullable = false)
    @NotEmpty(message = "Email may not be empty")
    private String email;
    @NotNull(message = "Date of bi may not be null")
    @NonNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "groups_id")
    private Group group;
    @Transient
    private Integer age;

    public Student(Long id, @NonNull String name, @NonNull String email, @NonNull LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(@NonNull String name, @NonNull String email, @NonNull LocalDate dateOfBirth, Group group) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.group = group;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
