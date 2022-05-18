package com.example.demo.group;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private GroupRepository mockGroupRepository;
    private StudentRepository mockStudentRepository;
    private GroupMapper mockGroupMapper;
    private GroupService testee;

    @BeforeEach
    void setup() {
        mockGroupRepository = mock(GroupRepository.class);
        mockStudentRepository = mock(StudentRepository.class);
        mockGroupMapper = mock(GroupMapper.class);
        testee = new GroupService(mockGroupRepository, mockGroupMapper, mockStudentRepository);
    }

    @Test
    void getGroups() {
    }

    @Test
    void getGroupById() {
    }

    @Test
    void addNewGroup() {
        Group group = new Group();
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), students);

        when(mockGroupMapper.toGroup(groupDto)).thenReturn(group);

        testee.addNewGroup(groupDto);

        verify(mockGroupRepository, times(1)).save(group);
    }

    @Test
    void deleteGroup() {
        Long groupId = 1L;
        Group group = new Group();
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));

        testee.deleteGroup(groupId);

        verify(mockGroupRepository, times(1)).deleteById(groupId);
    }

    @Test
    void deleteGroup_WhenGroupDontExistsInDb_ThenThrowIllegalStateException() {
        Long groupId = 3L;
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.empty());

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.deleteGroup(groupId));

        assertEquals("group with id " + groupId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void updateGroup_WhenGroupExistsInDb_ThenUpdateGroupSuccessfully() {
        Long groupId = 1L;
        Group group = new Group();
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18))));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));

        testee.updateGroup(groupId, groupDto);

        assertEquals("name1", group.getName());
        assertEquals(LocalDate.of(2020, 2, 18), group.getDateOfCreation());
    }

    @Test
    void updateGroup_WhenGroupDontExistsInDb_ThenThrowIllegalStateException() {
        Long groupId = 3L;
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.empty());

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.updateGroup(groupId, new GroupDto("name", LocalDate.of(2020, 2, 18))));

        assertEquals("group with id " + groupId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void addStudentToGroup_WhenGroupAndStudentExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Group group = new Group(1L, "name4", LocalDate.of(2020, 2, 18));
        Student student = new Student(1L, "name5", "email5", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        testee.addStudentToGroup(groupId, studentId);
        assertEquals(group.getId(), student.getGroup().getId());
        assertEquals(group.getStudentList(), List.of(student));
    }

    @Test
    void addStudentToGroup_WhenGroupDoesNotExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Student student = new Student(1L, "name5", "email5", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.empty());
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.addStudentToGroup(groupId, studentId));
        assertEquals("group with id " + groupId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void addStudentToGroup_WhenStudentDoesNotExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Group group = new Group(1L, "name4", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.empty());
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.addStudentToGroup(groupId, studentId));
        assertEquals("student with id " + studentId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void removeStudent_WhenStudentDoesNotExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Group group = new Group(1L, "name4", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.empty());
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.removeStudentFromGroup(groupId, studentId));
        assertEquals("student with id " + studentId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void removeStudentFromGroup_WhenGroupDoesNotExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Student student = new Student(1L, "name5", "email5", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.empty());
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.removeStudentFromGroup(groupId, studentId));
        assertEquals("group with id " + groupId + " does not exists", illegalStateException.getMessage());
    }

    @Test
    void removeStudentFromGroup_WhenGroupAndStudentExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Group group = new Group(1L, "name4", LocalDate.of(2020, 2, 18));
        Student student = new Student(1L, "name5", "email5", LocalDate.of(2020, 2, 18), group);
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        testee.removeStudentFromGroup(groupId, studentId);
        assertNull(student.getGroup());
        assertEquals(group.getStudentList(), List.of());
    }

    @Test
    void removeStudentFromGroup_WhenStudentNotInGroupAndExistsInDb() {
        Long groupId = 4L;
        Long studentId = 5L;
        Group group = new Group(1L, "name4", LocalDate.of(2020, 2, 18));
        Student student = new Student(1L, "name5", "email5", LocalDate.of(2020, 2, 18));
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(mockStudentRepository.findById(studentId)).thenReturn(Optional.of(student));
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.removeStudentFromGroup(groupId, studentId));
        assertEquals("student with id " + studentId + " not in group with id " + groupId, illegalStateException.getMessage());
    }
}