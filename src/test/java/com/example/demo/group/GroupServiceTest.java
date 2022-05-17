package com.example.demo.group;

import com.example.demo.student.StudentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private GroupRepository mockGroupRepository;
    private GroupMapper mockGroupMapper;
    private GroupService testee;

    @BeforeEach
    void setup() {
        mockGroupRepository = mock(GroupRepository.class);
        mockGroupMapper = mock(GroupMapper.class);
        testee = new GroupService(mockGroupRepository, mockGroupMapper);
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
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18))));

        when(mockGroupMapper.toGroup(groupDto)).thenReturn(group);

        testee.addNewGroup(groupDto);

        verify(mockGroupRepository, times(1)).save(group);
    }

    @Test
    void deleteGroup() {
        Long groupId = 1L;
        when(mockGroupRepository.existsById(groupId)).thenReturn(true);

        testee.deleteGroup(groupId);

        verify(mockGroupRepository, times(1)).deleteById(groupId);
    }

    @Test
    void deleteGroup_WhenGroupDontExistsInDb_ThenThrowIllegalStateException() {
        Long groupId = 3L;
        when(mockGroupRepository.existsById(groupId)).thenReturn(false);

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
        assertEquals(groupDto.getStudentList().stream().map(mockGroupMapper.studentMapper::toStudent).collect(Collectors.toList()), group.getStudentList());
    }

    @Test
    void updateGroup_WhenGroupDontExistsInDb_ThenThrowIllegalStateException() {
        Long groupId = 3L;
        when(mockGroupRepository.findById(groupId)).thenReturn(Optional.empty());

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
                () -> testee.updateGroup(groupId, new GroupDto("name", LocalDate.of(2020, 2, 18))));

        assertEquals("group with id " + groupId + " does not exists", illegalStateException.getMessage());
    }
}