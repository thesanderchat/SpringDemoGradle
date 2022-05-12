package com.example.demo.student;

import com.example.demo.group.Group;
import com.example.demo.group.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentRepository mockStudentRepository;
    private GroupRepository mockGroupRepository;
    private StudentMapper mockStudentMapper;
    private StudentService testee;

    @BeforeEach
    void setup() {
        mockStudentRepository = mock(StudentRepository.class);
        mockGroupRepository = mock(GroupRepository.class);
        mockStudentMapper = mock(StudentMapper.class);
        testee = new StudentService(mockStudentRepository, mockStudentMapper, mockGroupRepository);
    }

    @Test
    void getStudents() {
    }


    @Test
    void addNewStudent_WhenStudentDontExistInDb() {
        Group group = new Group(1L, "group1", LocalDate.of(2020, 2, 18));
        Student student = new Student();
        StudentDto studentDto = new StudentDto("name", "email", LocalDate.of(2020, 2, 18), 2L);

        when(mockGroupRepository.findById(studentDto.getGroupId())).thenReturn(Optional.of(group));
        when(mockStudentMapper.toStudent(studentDto)).thenReturn(student);

        testee.addNewStudent(studentDto);

        verify(mockStudentRepository, times(1)).save(student);
    }
    @Test
    void addNewStudent_WhenStudentExistInDbGroupDontExist() {
        Group group = new Group(1L, "group1", LocalDate.of(2020, 2, 18));
        Student student = new Student("name1", "email1", LocalDate.of(2020, 2, 18), group);
        StudentDto studentDto = new StudentDto("name", "email", LocalDate.of(2020, 2, 18), 2L);

        when(mockGroupRepository.findById(studentDto.getGroupId())).thenReturn(Optional.empty());

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.addNewStudent(studentDto));

        assertEquals("group with id " + studentDto.getGroupId() + " does not exists", illegalStateException.getMessage());
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
        Long studentId = 1L;
        Group group = new Group(1L, "group1", LocalDate.of(2020, 2, 18));
        Student student = new Student("name1", "email1", LocalDate.of(2020, 2, 18), group);

        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(mockGroupRepository.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

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
    void updateStudent_WhenGroupDontExistsInDb_ThenThrowIllegalStateException() {
        Group group = new Group(1L, "group1", LocalDate.of(2020, 2, 18));
        Student student = new Student("name1", "email1", LocalDate.of(2020, 2, 18), group);
        Long studentId = 3L;
        StudentDto studentDto = new StudentDto("name", "email", LocalDate.of(2020, 2, 18), 2L);
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(mockGroupRepository.findById(studentDto.getGroupId())).thenReturn(Optional.empty());

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.updateStudent(studentId, studentDto));

        assertEquals("group with id " + studentDto.getGroupId() + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void updateStudent_WhenStudentExistsInDb_ThenUpdateStudentSuccessfully() {
        Student student = new Student();
        Long studentId = 1L;
        Group group = new Group();
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        StudentDto studentDto = new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(group.getId())).thenReturn(Optional.of(group));

        testee.updateStudent(studentId, studentDto);

        assertEquals("name1", student.getName());
        assertEquals("email1", student.getEmail());
        assertEquals(LocalDate.of(2020, 2, 18), student.getDateOfBirth());
        assertEquals(studentDto.getGroupId(), student.getGroup().getId());

    }
}