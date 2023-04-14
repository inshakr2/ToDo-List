package com.rest.ToDoList.service;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.domain.ToDoStatus;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.dto.ToDoUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;



public interface ToDoService {

    ToDo makeToDoList(ToDoDto dto);

    PagedModel<EntityModel<ToDo>> pagingToDoList(Pageable pageable);

    ToDo findToDoById(Long id);

    ToDo updateToDo(Long id, ToDoUpdateRequest toDoUpdateRequest);

    ToDo changeToDoStatus(Long id);

    int deleteToDo(Long id);
}
