package com.example.demo.student;

import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toStudentDto(Student student) {
        Long groupId;
        groupId = student.getGroup() != null ? student.getGroup().getId() : null;
        return new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getDateOfBirth(), groupId);
    }

    public Student toStudent(StudentDto studentDto) {
        return new Student(studentDto.getId(), studentDto.getName(), studentDto.getEmail(), studentDto.getDateOfBirth());
    }
}
