package com.example.demo.group;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDto;
import com.example.demo.student.StudentService;
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

import javax.persistence.Table;
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
    void getGroups() throws Exception {
        List<GroupDto> result = List.of(new GroupDto("name1", LocalDate.of(2020, 2, 18), List.of(new Student("name1", "email1", LocalDate.of(2020, 2, 18)))));
        when(mockGroupService.getGroups()).thenReturn(result);
        this.mockMvc.perform(get("/groups"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(result)));
    }


    @Test
    void registerNewGroup() throws Exception {
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), List.of(new Student("name1", "email1", LocalDate.of(2020, 2, 18))));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void registerNewGroup_WhenValidationFailed_ThenReturnBadStatus() throws Exception {
        GroupDto groupDto = new GroupDto("", LocalDate.of(2020, 2, 18), List.of(new Student("name1", "email1", LocalDate.of(2020, 2, 18))));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteGroup() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/groups/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateGroup() throws Exception {
        GroupDto groupDto = new GroupDto("name1", LocalDate.of(2020, 2, 18), List.of(new Student("name1", "email1", LocalDate.of(2020, 2, 18))));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups/1")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateGroup_WhenValidationFailed_ThenReturnBadStatus() throws Exception {
        GroupDto groupDto = new GroupDto("", LocalDate.of(2020, 2, 18), List.of(new Student("name1", "email1", LocalDate.of(2020, 2, 18))));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups/1")
                        .content(toJsonString(groupDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(object);
    }
}