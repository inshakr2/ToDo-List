package com.rest.ToDoList.utils;

import com.rest.ToDoList.dto.ToDoDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ToDoValidator {

    public void validate(ToDoDto toDoDto, BindingResult bindingResult) {

        if (toDoDto.getEndDateTime().isBefore(toDoDto.getEnrollmentDateTime())) {
            bindingResult.rejectValue("endDateTime", "wrongValue", "endDateTime is wrong");
        }
    }
}
