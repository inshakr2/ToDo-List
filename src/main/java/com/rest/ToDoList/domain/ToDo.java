package com.rest.ToDoList.domain;

import com.rest.ToDoList.dto.ToDoDto;
import com.rest.ToDoList.dto.ToDoUpdateRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;


@Getter @Entity
@Setter(PROTECTED)
@DynamicUpdate
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class ToDo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private LocalDateTime enrollmentDateTime;
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private ToDoStatus toDoStatus;

    public static ToDo createTask(ToDoDto dto) {

        ToDo toDo = new ToDo();
        toDo.setName(dto.getName());
        toDo.setDescription(dto.getDescription());
        toDo.setEnrollmentDateTime(dto.getEnrollmentDateTime());
        toDo.setEndDateTime(dto.getEndDateTime());
        toDo.setToDoStatus(ToDoStatus.TODO);

        return toDo;
    }

    public void updateTask(ToDoUpdateRequest dto) {

        if (dto.getName() != null && !dto.getName().isBlank()) {
            this.name = dto.getName();
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            this.description = dto.getDescription();
        }

        if (dto.getEndDateTime() != null) {
            this.endDateTime = dto.getEndDateTime();
        }

    }

    public void doneOrUnDone() {
        if (this.toDoStatus == ToDoStatus.TODO) {
            this.toDoStatus = ToDoStatus.FINISHED;
        } else {
            this.toDoStatus = ToDoStatus.TODO;
        }
    }
}
