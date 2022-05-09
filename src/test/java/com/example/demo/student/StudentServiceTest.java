package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentRepository mockStudentRepository;
    private StudentMapper mockStudentMapper;
    private StudentService testee;

    @BeforeEach
    void setup() {
        mockStudentRepository = mock(StudentRepository.class);
        mockStudentMapper = mock(StudentMapper.class);
        testee = new StudentService(mockStudentRepository, mockStudentMapper);
    }

    @Test
    void getStudents() {
    }

    @Test
    void addNewStudent_WhenStudentExistInDb_ThenThrowIllegalStateException() {
        when(mockStudentRepository.findStudentByEmail("email")).thenReturn(Optional.of(new Student()));

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.addNewStudent(new StudentDto("", "email", LocalDate.of(2020, 2, 18))));
        assertEquals("email taken", illegalStateException.getMessage());
    }

    @Test
    void addNewStudent_WhenStudentDontExistInDb() {
        Student student = new Student();
        when(mockStudentRepository.findStudentByEmail("email")).thenReturn(Optional.empty());
        StudentDto studentDto = new StudentDto("name", "email", LocalDate.of(2020, 2, 18));
        when(mockStudentMapper.toStudent(studentDto)).thenReturn(student);
        testee.addNewStudent(studentDto);
        verify(mockStudentRepository, times(1)).save(student);
    }

    @Test
    void deleteStudent_WhenStudentDontExistsInDb_ThenThrowIllegalStateException() {
        Long studentId = 3L;
        when(mockStudentRepository.existsById(studentId)).thenReturn(false);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.deleteStudent(studentId));
        assertEquals("student with id " + studentId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void deleteStudent_WhenStudentExistsInDb_ThenVerifyDeleting() {
        Long studentId = 3L;
        when(mockStudentRepository.existsById(studentId)).thenReturn(true);

        testee.deleteStudent(studentId);

        verify(mockStudentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void updateStudent_WhenStudentDontExistsInDb_ThenThrowIllegalStateException() {
        Long studentId = 3L;
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.empty());
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.updateStudent(studentId, new StudentDto("name", "email", LocalDate.of(2020, 2, 18))));
        assertEquals("student with id " + studentId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void updateStudent_WhenStudentExistsInDb_ThenUpdateStudentSuccessfully() {
        Student student = new Student();
        Long studentId = 1L;
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        StudentDto studentDto = new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18));
        testee.updateStudent(studentId, studentDto);
        assertEquals("name1", student.getName());
        assertEquals("email1", student.getEmail());
        assertEquals(LocalDate.of(2020, 2, 18), student.getDateOfBirth());

    }
}