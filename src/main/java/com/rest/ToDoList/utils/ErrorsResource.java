package com.rest.ToDoList.utils;

import com.rest.ToDoList.controller.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorsResource extends EntityModel<BindingResult> {
    public ErrorsResource(BindingResult content, Link... links) {
        super(content, Arrays.asList(links));
        add((linkTo(methodOn(IndexController.class).index())).withRel("index"));
    }
}