package com.rest.ToDoList.controller;

import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/todo", produces = MediaTypes.HAL_JSON_VALUE)
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping
    public ResponseEntity createToDo(@RequestBody @Valid ToDoDto toDoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        ToDo toDo = toDoService.makeToDoList(toDoDto);
        URI createdUri = linkTo(ToDoController.class).slash(toDo.getId()).toUri();

        return ResponseEntity.created(createdUri).body(toDo);
    }
}
