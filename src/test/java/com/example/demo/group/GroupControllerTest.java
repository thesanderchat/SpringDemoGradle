package com.example.demo.group;

import com.example.demo.student.StudentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService mockGroupService;

    @Test
    void getGroups_WhenGot_ThenCheckStatusAndContent() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        List<GroupDto> result = List.of(new GroupDto("name1", LocalDate.of(2020, 2, 18), students));
        when(mockGroupService.getGroups()).thenReturn(result);
        this.mockMvc.perform(get("/groups"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(result)));
    }

    @Test
    void getGroupById_WhenGot_ThenCheckStatusAndContent() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto result = new GroupDto(1L, "name1", LocalDate.of(2020, 2, 18), students);
        when(mockGroupService.getGroupById(1L)).thenReturn(result);
        this.mockMvc.perform(get("/groups/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(result)));
    }

    @Test
    void registerNewGroup_WhenDone_ThenCheckIsItCreated() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name2", "email2", LocalDate.of(2020, 2, 19)));
        GroupDto groupDto = new GroupDto("name2", LocalDate.of(2020, 2, 19), students);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void registerNewGroup_WhenNameValidationFailed_ThenReturnBadStatus() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("", LocalDate.of(2020, 2, 18), students);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerNewGroup_WhenDateOfCreationValidationFailed_ThenReturnBadStatus() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("name7", LocalDate.of(2020, 2, 18), students);
        groupDto.setDateOfCreation(null);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteGroup_WhenDone_ThenCheckIsItDeleted() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/groups/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroup_WhenValidationOk_ThenCheckIsItCreated() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name3", "email3", LocalDate.of(2020, 2, 20)));
        GroupDto groupDto = new GroupDto("name3", LocalDate.of(2020, 2, 20), students);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups/1")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateGroup_WhenNameValidationFailed_ThenReturnBadStatus() throws Exception {
        List<StudentDto> students = List.of(new StudentDto("name1", "email1", LocalDate.of(2020, 2, 18)));
        GroupDto groupDto = new GroupDto("", LocalDate.of(2020, 2, 18), students);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups/1")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void addNewStudentToGroup_WhenDone_CheckIsItCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups/1/1"))
                .andExpect(status().isCreated());

    }

    @Test
    void removeStudentFromGroup_WhenDone_CheckIsItDeleted() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/groups/1/1"))
                .andExpect(status().isNoContent());

    }

    private String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(object);
    }
}