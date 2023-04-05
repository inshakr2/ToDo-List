package com.rest.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ToDoUpdateRequest {


    private String name;

    private String description;

    private LocalDateTime endDateTime;

    public ToDoUpdateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
