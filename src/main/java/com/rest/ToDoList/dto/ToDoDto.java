package com.rest.ToDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ToDoDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    private LocalDateTime enrollmentDateTime = LocalDateTime.now().withNano(0);

    // 기본 설정 오늘 자정까지
    private LocalDateTime endDateTime = generateMidnight();

    public ToDoDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ToDoDto(String name, String description, LocalDateTime enrollmentDateTime) {
        this.name = name;
        this.description = description;
        this.enrollmentDateTime = enrollmentDateTime;
    }

    private LocalDateTime generateMidnight() {


        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String midnight = LocalDate.now().plusDays(1).atStartOfDay().format(pattern);

        return LocalDateTime.parse(midnight);
    }
}
