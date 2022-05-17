package com.example.demo.group;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupMapperTest {
    private final StudentMapper studentMapper = new StudentMapper();
    private final GroupMapper testee = new GroupMapper(studentMapper);

    @Test
    void toGroupDto() {
        List<Student> actual = new ArrayList<>();
        actual.add(new Student("studentName1", "email1", LocalDate.of(2020, 2, 18)));
        Group group = new Group("groupName1", LocalDate.of(2021, 2, 18),actual);
        GroupDto groupDto = testee.toGroupDto(group);
        assertEquals("groupName1", groupDto.getName());
        assertEquals(LocalDate.of(2021, 2, 18), groupDto.getDateOfCreation());
    }

    @Test
    void toGroup() {
        List<StudentDto> actual = new ArrayList<>();
        actual.add(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18),actual);
        Group group = testee.toGroup(groupDto);
        assertEquals("name1", group.getName());
        assertEquals(LocalDate.of(2020, 2, 18), group.getDateOfCreation());
    }
}