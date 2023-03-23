package com.rest.ToDoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.ToDoList.common.RestDocConfiguration;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
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
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-list").exists())
                .andExpect(jsonPath("_links.update").exists())
                .andExpect(jsonPath("_links.status").exists())
                .andDo(document("create-ToDo",
                                links(
                                        linkWithRel("self").description("link to self"),
                                        linkWithRel("query-list").description("link to query list"),
                                        linkWithRel("update").description("link to update an existing ToDo"),
                                        linkWithRel("status").description("link to change ToDo Status")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.ACCEPT).description("Accept Header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type Header")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("Name of ToDo"),
                                        fieldWithPath("description").description("Description of New ToDo"),
                                        fieldWithPath("enrollmentDateTime").description("Date Time of Enrollment ToDo"),
                                        fieldWithPath("endDateTime").description("Deadline for ToDo")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.LOCATION).description("Location Header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("Identifier of New ToDo"),
                                        fieldWithPath("name").description("Name of ToDo"),
                                        fieldWithPath("description").description("Description of New ToDo"),
                                        fieldWithPath("enrollmentDateTime").description("Date Time of Enrollment ToDo"),
                                        fieldWithPath("endDateTime").description("Deadline for ToDo"),
                                        fieldWithPath("toDoStatus").description("It Tells the current status of ToDo"),
                                        fieldWithPath("_links.*").ignored(),
                                        fieldWithPath("_links.self.*").ignored(),
                                        fieldWithPath("_links.query-list.*").ignored(),
                                        fieldWithPath("_links.update.*").ignored(),
                                        fieldWithPath("_links.status.*").ignored()
                                )
                        ))
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
                .andExpect(jsonPath("_links.index").exists())
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
                .andExpect(jsonPath("_links.index").exists())
        ;
    }

}
