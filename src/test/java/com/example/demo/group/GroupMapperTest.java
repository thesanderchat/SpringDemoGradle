package com.example.demo.group;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GroupMapperTest {
    @Mock
    private StudentMapper mockStudentMapper = new StudentMapper();
    @InjectMocks
    private GroupMapper testee = new GroupMapper(mockStudentMapper);

    @Test
    void toGroupDto_WhenAllGood_ThenCreateNewGroupDtoSuccessfully() {
        Group group = new Group(1L, "groupName1", LocalDate.of(2021, 2, 18));
        Student student = new Student(1L, "studentName1", "email1", LocalDate.of(2020, 2, 18));
        group.addNewStudentToStudentList(student);
        StudentDto studentDto = new StudentDto(1L, "studentName1", "email1", LocalDate.of(2020, 2, 18), group.getId());

        GroupDto groupDto = testee.toGroupDto(group);

        assertEquals(group.getName(), groupDto.getName());
        assertEquals(group.getDateOfCreation(), groupDto.getDateOfCreation());
        assertEquals(List.of(studentDto), groupDto.getStudentList());
    }

    @Test
    void toGroup_WhenAllGood_ThenCreateNewGroupSuccessfully() {
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18));
        Group group = testee.toGroup(groupDto);
        assertEquals("name1", group.getName());
        assertEquals(LocalDate.of(2020, 2, 18), group.getDateOfCreation());
    }
}