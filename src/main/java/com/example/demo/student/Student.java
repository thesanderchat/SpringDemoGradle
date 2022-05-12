package com.example.demo.student;

import com.example.demo.group.Group;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name = "students")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    private Long id;
    @NonNull
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NonNull
    @NotEmpty(message = "Email may not be empty")
    private String email;
    @NonNull
    private LocalDate dateOfBirth;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "groups_id")
    private Group group;
    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

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

    public Student() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
