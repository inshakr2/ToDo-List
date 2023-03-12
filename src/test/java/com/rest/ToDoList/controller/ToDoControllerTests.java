package com.rest.ToDoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.ToDoList.controller.ToDoController;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void createToDo() throws Exception {

        ToDoDto dto = new ToDoDto("Test ToDo List", "Test");

//        ToDo toDo = ToDo.createTask(dto);
//        Mockito.when(toDoService.makeToDoList(dto)).thenReturn(toDo);

        mockMvc.perform(post("/api/todo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        ;
    }

    @Test
    public void createToDo_Bad_Request_Out_Of_DtoValue() throws Exception {

        ToDoDto dto = new ToDoDto("Test ToDo List", "Test" );
        ToDo toDo = ToDo.createTask(dto);

        mockMvc.perform(post("/api/todo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void createToDo_Bad_Request_Empty_Input() throws Exception {

        ToDoDto dto = new ToDoDto();

        mockMvc.perform(post("/api/todo")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void createToDo_Bad_Request_Wrong_Input() throws Exception {

        ToDoDto dto = new ToDoDto("Test ToDo List", "Test",
                                LocalDateTime.now(),
                                LocalDateTime.now().minusDays(10));

        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

}
