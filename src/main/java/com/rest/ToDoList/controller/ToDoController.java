package com.rest.ToDoList.controller;

import com.rest.ToDoList.domain.ToDoStatus;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoUpdateRequest;
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
import org.springframework.web.bind.annotation.*;

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
        toDoResource.add(Link.of("/docs/index.html#resources-todo-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(toDoResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity getToDo(@PathVariable Long id) {

        ToDo entity = toDoService.findToDoById(id);

        if (entity == null) {
            return ResponseEntity.notFound().build();
        }

        ToDoResource toDoResource = new ToDoResource(entity);
        toDoResource.add(Link.of("/docs/index.html#resources-todo-get").withRel("profile"));
        return ResponseEntity.ok(toDoResource);

    }

    @PutMapping("/{id}")
    public ResponseEntity updateToDo(@PathVariable Long id,
                                     @RequestBody ToDoUpdateRequest toDoUpdateRequest) {

        ToDo updatedToDo = toDoService.updateToDo(id, toDoUpdateRequest);

        if (updatedToDo == null) {
            return ResponseEntity.notFound().build();
        }

        ToDoResource toDoResource = new ToDoResource(updatedToDo);
        toDoResource.add(Link.of("/docs/index.html#resources-todo-update").withRel("profile"));

        return ResponseEntity.ok(toDoResource);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity changeStatus(@PathVariable Long id) {

        ToDo toDo = toDoService.changeToDoStatus(id);

        if (toDo == null) {
            return ResponseEntity.notFound().build();
        }

        ToDoResource toDoResource = new ToDoResource(toDo);
        toDoResource.add(Link.of("/docs/index.html#resources-todo-status-change").withRel("profile"));

        return ResponseEntity.ok(toDoResource);
    }

    @GetMapping
    public ResponseEntity queryToDo(Pageable pageable) {

        PagedModel<EntityModel<ToDo>> entityModels = toDoService.pagingToDoList(pageable);
        entityModels.add(Link.of("/docs/index.html#resources-todo-list").withRel("profile"));

        return ResponseEntity.ok(entityModels);
    }
}
