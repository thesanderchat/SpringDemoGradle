package com.example.demo.group;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupService {
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDto> getGroups() {
        return groupRepository.findAllByOrderByIdAsc().stream()
                .map(groupMapper::toGroupDto)
                .collect(Collectors.toList());
    }

    public GroupDto getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .map(groupMapper::toGroupDto).orElseThrow(() -> new IllegalStateException("No group"));
    }

    public void addNewGroup(GroupDto groupDto) {
        groupRepository.save(groupMapper.toGroup(groupDto));
    }

    public void deleteGroup(Long groupId) {
        boolean exists = groupRepository.existsById(groupId);
        if (!exists) {
            throw new IllegalStateException("group with id " + groupId + " does not exists");
        }
        groupRepository.deleteById(groupId);
    }

    @Transactional
    public void updateGroup(Long groupId, @Valid GroupDto groupDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalStateException("group with id " + groupId + " does not exists"));
        group.setName(groupDto.getName());
        group.setDateOfCreation(groupDto.getDateOfCreation());
        group.addNewListOfStudentsToStudentList(groupDto.getStudentList().stream().map(groupMapper.studentMapper::toStudent).collect(Collectors.toList()));
    }
}