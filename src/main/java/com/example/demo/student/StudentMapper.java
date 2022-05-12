package com.example.demo.student;

import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toStudentDto(Student student){
        return new StudentDto(student.getName(), student.getEmail(),student.getDateOfBirth(),student.getGroup().getId());
    }
    public Student toStudent(StudentDto studentDto){
        return new Student(studentDto.getName(), studentDto.getEmail(),studentDto.getDateOfBirth());
    }
}
