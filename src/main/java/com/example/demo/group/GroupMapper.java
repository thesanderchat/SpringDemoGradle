package com.example.demo.group;

import com.example.demo.group.Group;
import com.example.demo.group.GroupDto;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public GroupDto toGroupDto(Group group){
        return new GroupDto(group.getName(),group.getDateOfCreation(),group.getStudentList());
    }
    public Group toGroup(GroupDto groupDto){
        return new Group(groupDto.getName(),groupDto.getDateOfCreation(),groupDto.getStudentList());
    }
}
