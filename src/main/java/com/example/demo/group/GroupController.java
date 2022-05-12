package com.example.demo.group;


import com.example.demo.student.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    private List<GroupDto> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping(path = "{groupId}")
    private GroupDto getGroups(@PathVariable("groupId") Long groupId) {
        return groupService.getGroupById(groupId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewGroup(@RequestBody @Valid GroupDto groupDto) {
        groupService.addNewGroup(groupDto);
    }

    @DeleteMapping(path = "{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable("groupId") Long groupId) {
        groupService.deleteGroup(groupId);
    }

    @PutMapping(path = "{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateGroup(@PathVariable("groupId") Long grouoId,
                            @RequestBody @Valid GroupDto groupDto) {
        groupService.updateGroup(grouoId, groupDto);
    }
}