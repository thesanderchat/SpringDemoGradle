package com.example.demo.group;

import com.example.demo.student.Student;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
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
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Date of creation may not be null")
    @NonNull
    @Column(nullable = false)
    private LocalDate dateOfCreation;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> studentList = new ArrayList<>();

    public Group(Long id, @NonNull String name, @NonNull LocalDate dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public void addNewStudentToStudentList(Student student) {
        studentList.add(student);
        student.setGroup(this);
    }

    public void removeStudentFromStudentList(Student student) {
        studentList.remove(student);
        student.setGroup(null);
    }

    public void removeAllStudentsFromStudentList() {
        for (Student student : this.studentList
        ) {
            student.setGroup(null);
        }
        this.setStudentList(null);
    }
}
