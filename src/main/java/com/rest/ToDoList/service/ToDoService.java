package com.rest.ToDoList.service;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;



public interface ToDoService {

    ToDo makeToDoList(ToDoDto dto);

    PagedModel<EntityModel<ToDo>> pagingToDoList(Pageable pageable);
}
