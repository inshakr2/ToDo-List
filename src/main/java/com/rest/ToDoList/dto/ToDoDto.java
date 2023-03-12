package com.rest.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ToDoDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    private LocalDateTime enrollmentDateTime = LocalDateTime.now();

    // 기본 설정 오늘 자정까지
    private LocalDateTime endDateTime = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);

    public ToDoDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
