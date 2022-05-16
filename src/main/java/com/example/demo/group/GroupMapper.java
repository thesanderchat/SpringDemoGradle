package com.example.demo.group;

import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public GroupDto toGroupDto(Group group) {
        return new GroupDto(group.getId(), group.getName(), group.getDateOfCreation(), group.getStudentList());
    }

    public Group toGroup(GroupDto groupDto) {
        return new Group(groupDto.getId(), groupDto.getName(), groupDto.getDateOfCreation(), groupDto.getStudentList());
    }
}
