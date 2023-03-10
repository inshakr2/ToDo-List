package com.rest.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ToDoDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    private LocalDateTime enrollmentDateTime = LocalDateTime.now();


    public ToDoDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
