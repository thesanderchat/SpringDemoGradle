package com.example.demo.student;

import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toStudentDto(Student student) {
        Long groupId;
        if (student.getGroup() == null) {
            groupId = null;
        } else {
            groupId = student.getGroup().getId();
        }
        return new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getDateOfBirth(), groupId);
    }

    public Student toStudent(StudentDto studentDto) {
        return new Student(studentDto.getId(), studentDto.getName(), studentDto.getEmail(), studentDto.getDateOfBirth());
    }
}
