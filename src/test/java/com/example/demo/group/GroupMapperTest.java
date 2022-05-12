package com.example.demo.group;

import com.example.demo.student.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupMapperTest {
    private final GroupMapper testee = new GroupMapper();

    @Test
    void toGroupDto() {
        List<Student> actual = new java.util.ArrayList<>();
        actual.add(new Student("name1", "email1", LocalDate.of(2020, 2, 18)));
        Group group = new Group("name1", LocalDate.of(2020, 2, 18), actual);
        GroupDto groupDto = testee.toGroupDto(group);
        assertEquals("name1", groupDto.getName());
        assertEquals(LocalDate.of(2020, 2, 18), groupDto.getDateOfCreation());
        assertEquals(actual, groupDto.getStudentList());
    }

    @Test
    void toGroup() {
        List<Student> actual = new java.util.ArrayList<>();
        actual.add(new Student("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), actual);
        Group group = testee.toGroup(groupDto);
        assertEquals("name1", group.getName());
        assertEquals(LocalDate.of(2020, 2, 18), group.getDateOfCreation());
        assertEquals(actual, group.getStudentList());
    }
}