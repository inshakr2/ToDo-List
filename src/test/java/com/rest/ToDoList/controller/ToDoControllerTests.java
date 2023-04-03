package com.rest.ToDoList.controller;

import com.rest.ToDoList.common.BaseTestController;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.service.ToDoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ToDoControllerTests extends BaseTestController {

    @Autowired
    ToDoService toDoService;

    @Nested
    @DisplayName("ToDo 생성")
    class create {


        @Test
        @DisplayName("[201] ToDo 단건 생성")
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
                    .andExpect(jsonPath("_links.profile").exists())
                    .andDo(document("create-ToDo",
                            links(
                                    linkWithRel("self").description("link to self"),
                                    linkWithRel("query-list").description("link to query list"),
                                    linkWithRel("update").description("link to update an existing ToDo"),
                                    linkWithRel("status").description("link to change ToDo Status"),
                                    linkWithRel("profile").description("link to profile")
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
                                    fieldWithPath("_links.status.*").ignored(),
                                    fieldWithPath("_links.profile.*").ignored()
                            )
                    ))
            ;
        }

        @Test
        @DisplayName("[400] Request Body 오류")
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
        @DisplayName("[400] Request Body 빈값")
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
        @DisplayName("[400] Request 필드 Validation 오류")
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

    @Test
    @DisplayName("[200] ToDo를 30건 생성하고, 페이징 처리")
    public void queryPagingToDo() throws Exception {

        IntStream.range(0, 30).forEach(this::generateToDo);

        this.mockMvc.perform(get("/api/todo")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-list"))
        ;

    }

    @Test
    @DisplayName("[200] ToDo 단건 조회")
    public void getToDo() throws Exception {
        ToDo toDo = this.generateToDo(100);

        this.mockMvc.perform(get("/api/todo/{id}", toDo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                ;
    }

    @Test
    @DisplayName("[200] ToDo 수정")
    public void updateToDo() throws Exception {
        ToDo toDo = this.generateToDo(200);

        String update = "Update";
        ToDoDto dto = new ToDoDto(update, update);
        this.mockMvc.perform(put("/api/todo/{id}", toDo.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(update))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                ;
    }

    private ToDo generateToDo(int index) {
        String idx = Integer.toString(index);
        return toDoService.makeToDoList(new ToDoDto("ToDo" + idx, "Test ToDo"));
    }

}
