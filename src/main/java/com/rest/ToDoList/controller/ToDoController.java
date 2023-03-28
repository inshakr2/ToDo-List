package com.rest.ToDoList.controller;

import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.service.ToDoService;
import com.rest.ToDoList.utils.ErrorsResource;
import com.rest.ToDoList.utils.ToDoResource;
import com.rest.ToDoList.utils.ToDoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/todo", produces = MediaTypes.HAL_JSON_VALUE)
public class ToDoController {

    private final ToDoService toDoService;
    private final ToDoValidator toDoValidator;

    @PostMapping
    public ResponseEntity createToDo(@RequestBody @Valid ToDoDto toDoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(bindingResult));
        }

        toDoValidator.validate(toDoDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(bindingResult));
        }

        ToDo toDo = toDoService.makeToDoList(toDoDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ToDoController.class).slash(toDo.getId());
        URI createdUri = selfLinkBuilder.toUri();

        // HATEOAS 적용
        ToDoResource toDoResource = new ToDoResource(toDo);
        toDoResource.add(linkTo(ToDoController.class).withRel("query-list"));
        toDoResource.add(selfLinkBuilder.withRel("update"));
        toDoResource.add(selfLinkBuilder.slash("status").withRel("status"));

        return ResponseEntity.created(createdUri).body(toDoResource);
    }

    @GetMapping
    public ResponseEntity queryToDo(Pageable pageable) {

        PagedModel<EntityModel<ToDo>> entityModels = toDoService.pagingToDoList(pageable);
        entityModels.add(Link.of("/docs/index.html#resources-todo").withRel("profile"));

        return ResponseEntity.ok(entityModels);
    }
}
