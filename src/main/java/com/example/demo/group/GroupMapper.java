package com.example.demo.group;

import com.example.demo.student.StudentMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class GroupMapper {
    StudentMapper studentMapper;

    public GroupDto toGroupDto(Group group) {
        return new GroupDto(group.getId(), group.getName(), group.getDateOfCreation());
    }

    public Group toGroup(GroupDto groupDto) {
        return new Group(groupDto.getId(), groupDto.getName(), groupDto.getDateOfCreation());
    }
}
