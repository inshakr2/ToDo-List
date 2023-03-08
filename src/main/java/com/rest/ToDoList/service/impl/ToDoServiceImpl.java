package com.rest.ToDoList.service.impl;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.repository.ToDoRepository;
import com.rest.ToDoList.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;

    @Override
    public ToDo makeToDoList(ToDoDto dto) {

        ToDo toDo = toDoRepository.save(ToDo.createTask(dto));
        
        return toDo;
    }
}
