package com.rest.ToDoList.domain;

import com.rest.ToDoList.dto.ToDoDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;


@Getter @Entity
@Setter(PROTECTED)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class ToDo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private LocalDateTime enrollmentDateTime;

    @Enumerated(EnumType.STRING)
    private ToDoStatus toDoStatus;

    public static ToDo createTask(ToDoDto dto) {

        ToDo toDo = new ToDo();
        toDo.setName(dto.getName());
        toDo.setDescription(dto.getDescription());
        toDo.setEnrollmentDateTime(dto.getEnrollmentDateTime());
        toDo.setToDoStatus(ToDoStatus.TODO);

        return toDo;
    }
}
