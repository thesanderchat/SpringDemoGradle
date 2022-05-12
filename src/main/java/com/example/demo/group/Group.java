package com.example.demo.group;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @NonNull
    private String name;
    @NonNull
    private LocalDate dateOfCreation;
    @NonNull
    @JsonManagedReference
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> studentList = new ArrayList<>();

    public Group() {

    }

    public Group(Long id, @NonNull String name, @NonNull LocalDate dateOfCreation, @NonNull List<Student> studentList) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public Group(Long id, @NonNull String name, @NonNull LocalDate dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public Group(@NonNull String name, @NonNull LocalDate dateOfCreation, @NonNull List<Student> studentList) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.studentList = studentList;
    }

    public void addNewStudentToStudentList(Student student) {
        studentList.add(student);
        student.setGroup(this);
    }

    public void addNewListOfStudentsToStudentList(List<Student> students) {
        for (Student student : students
        ) {
            studentList.add(student);
            student.setGroup(this);
        }
    }

    public void removeStudentFromStudentList(Student student) {
        studentList.remove(student);
        student.setGroup(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return id != null && Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}