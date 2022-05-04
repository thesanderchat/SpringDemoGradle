package com.example.demo.student;

import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public StudentDto toStudentDto(Student student){
        return new StudentDto(student.getName(), student.getEmail(),student.getDateOfBirth());
    }
    public Student toStudent(StudentDto studentDto){
        return new Student(studentDto.getName(), studentDto.getEmail(),studentDto.getDateOfBirth());
    }
}
