package com.rest.ToDoList;

import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.todo.ToDo;
import com.rest.ToDoList.todo.ToDoStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ToDoListApplicationTests {

	@Test
	public void createTask() {
		ToDoDto dto = new ToDoDto("Test ToDo List", "Just Test", LocalDateTime.now());
		ToDo toDo = ToDo.creationTask(dto);

		assertThat(toDo).isNotNull();
		assertThat(toDo.getToDoStatus()).isEqualTo(ToDoStatus.TODO);
	}

}
