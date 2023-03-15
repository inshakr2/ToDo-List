package com.rest.ToDoList.utils;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.rest.ToDoList.controller.ToDoController;
import com.rest.ToDoList.domain.ToDo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ToDoResource extends EntityModel<ToDo> {

//    @JsonUnwrapped
//    private ToDo toDo;

    public ToDoResource(ToDo toDo, Link... links) {
        super(toDo, Arrays.asList(links));
        add(linkTo(ToDoController.class).slash(toDo.getId()).withSelfRel());
    }

}
