package com.rest.ToDoList.todo;

import com.rest.ToDoList.dto.ToDoDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter(PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id")
public class ToDo {

    private Long id;
    private String name;
    private String description;

    private LocalDateTime enrollmentDateTime;
    private ToDoStatus toDoStatus;

    public static ToDo creationTask(ToDoDto dto) {

        ToDo toDo = new ToDo();
        toDo.setName(dto.getName());
        toDo.setDescription(dto.getDescription());
        toDo.setEnrollmentDateTime(dto.getEnrollmentDateTime());
        toDo.setToDoStatus(ToDoStatus.TODO);

        return toDo;
    }
}
