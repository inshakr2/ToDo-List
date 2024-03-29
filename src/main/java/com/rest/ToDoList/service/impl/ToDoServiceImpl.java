package com.rest.ToDoList.service.impl;

import com.rest.ToDoList.domain.ToDo;
import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.dto.ToDoUpdateRequest;
import com.rest.ToDoList.repository.ToDoRepository;
import com.rest.ToDoList.service.ToDoService;
import com.rest.ToDoList.utils.ToDoResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final PagedResourcesAssembler<ToDo> assembler;

    @Override
    public ToDo makeToDoList(ToDoDto dto) {

        ToDo toDo = toDoRepository.save(ToDo.createTask(dto));

        return toDo;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<ToDo>> pagingToDoList(Pageable pageable) {

        Page<ToDo> page = toDoRepository.findAll(pageable);
        PagedModel<EntityModel<ToDo>> entityModels = assembler.toModel(page, e -> new ToDoResource(e));

        return entityModels;
    }

    @Override
    @Transactional(readOnly = true)
    public ToDo findToDoById(Long id) {

        Optional<ToDo> optionalToDo = toDoRepository.findById(id);

        if (optionalToDo.isEmpty()) {
            return null;
        } else {

            return optionalToDo.get();
        }

    }

    @Override
    public ToDo updateToDo(Long id, ToDoUpdateRequest toDoUpdateRequest) {

        Optional<ToDo> optionalToDo = toDoRepository.findById(id);

        if (optionalToDo.isEmpty()) {
            return null;
        }

        ToDo toDo = optionalToDo.get();
        toDo.updateTask(toDoUpdateRequest);

//        toDoRepository.save(toDo);

        return toDo;
    }

    @Override
    public ToDo changeToDoStatus(Long id) {

        Optional<ToDo> optionalToDo = toDoRepository.findById(id);

        if (optionalToDo.isEmpty()) {
            return null;
        }

        ToDo toDo = optionalToDo.get();
        toDo.doneOrUnDone();

        return toDo;
    }

    @Override
    public int deleteToDo(Long id) {

        Optional<ToDo> optionalToDo = toDoRepository.findById(id);

        if (optionalToDo.isEmpty()) {
            return 0;
        }

        toDoRepository.deleteById(id);

        return 1;
    }
}
