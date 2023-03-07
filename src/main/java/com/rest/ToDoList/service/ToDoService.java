package com.rest.ToDoList.service;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;

public interface ToDoService {

    ToDo makeToDoList(ToDoDto dto);
}
