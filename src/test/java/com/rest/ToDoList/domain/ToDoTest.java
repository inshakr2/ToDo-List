package com.rest.ToDoList.domain;

import com.rest.ToDoList.dto.ToDoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoTest {

    @Test
    public void createTask() {
        ToDoDto dto = new ToDoDto("Test ToDo List", "Just Test", LocalDateTime.now());
        ToDo toDo = ToDo.createTask(dto);

        assertThat(toDo).isNotNull();
        assertThat(toDo.getToDoStatus()).isEqualTo(ToDoStatus.TODO);
    }

}