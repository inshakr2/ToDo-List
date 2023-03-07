package com.rest.ToDoList.service.impl;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ToDoServiceImpl implements ToDoService {

    @Override
    public ToDo makeToDoList(ToDoDto dto) {

        ToDo toDo = ToDo.createTask(dto);
        return toDo;
    }
}
