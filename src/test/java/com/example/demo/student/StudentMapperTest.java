package com.example.demo.student;

import com.example.demo.group.Group;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {
    private final StudentMapper testee = new StudentMapper();

    @Test
    void toStudentDto() {
        Student student = new Student("name1", "email1", LocalDate.of(2020, 2, 18),new Group("group1",LocalDate.of(2020, 2, 18)));
        StudentDto studentDto = testee.toStudentDto(student);
        assertEquals("name1", studentDto.getName());
        assertEquals("email1", studentDto.getEmail());
        assertEquals(LocalDate.of(2020, 2, 18), studentDto.getDateOfBirth());
    }

    @Test
    void toStudent() {
        StudentDto studentDto = new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18),new Group("group1",LocalDate.of(2020, 2, 18)));
        Student student =  testee.toStudent(studentDto);
        assertEquals("name1", student.getName());
        assertEquals("email1", student.getEmail());
        assertEquals(LocalDate.of(2020, 2, 18), studentDto.getDateOfBirth());
    }
}