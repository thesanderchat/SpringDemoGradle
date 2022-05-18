package com.example.demo.group;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupMapperTest {
    @Mock
    private StudentMapper mockStudentMapper;
    private final GroupMapper testee = new GroupMapper(mockStudentMapper);

    /*@Test
    void toGroupDto() {
        Group group = new Group("groupName1", LocalDate.of(2021, 2, 18));
        Student student = new Student("studentName1", "email1", LocalDate.of(2020, 2, 18));
        StudentDto studentDto = new StudentDto("studentName1", "email1", LocalDate.of(2020, 2, 18));
        group.addNewStudentToStudentList(student);

        when(mockStudentMapper.toStudentDto(student)).thenReturn(studentDto);

        GroupDto groupDto = testee.toGroupDto(group);

        assertEquals(group.getName(), groupDto.getName());
        assertEquals(group.getDateOfCreation(), groupDto.getDateOfCreation());
        assertEquals(group.getStudentList(), groupDto.getStudentList());
    }*/

    @Test
    void toGroup() {
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18));
        Group group = testee.toGroup(groupDto);
        assertEquals("name1", group.getName());
        assertEquals(LocalDate.of(2020, 2, 18), group.getDateOfCreation());
    }
}