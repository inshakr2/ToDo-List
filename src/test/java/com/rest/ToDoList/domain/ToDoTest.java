package com.rest.ToDoList.domain;

import com.rest.ToDoList.dto.ToDoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoTest {

    @Test
    @DisplayName("단순 ToDo Entity 생성")
    public void createTask() {
        ToDoDto dto = new ToDoDto("Test ToDo List", "Just Test");
        ToDo toDo = ToDo.createTask(dto);

        assertThat(toDo).isNotNull();
        assertThat(toDo.getToDoStatus()).isEqualTo(ToDoStatus.TODO);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("단순 ToDo Entity 생성 (with parameterized)")
    public void testParams(String name, String description, LocalDateTime begin) {

        ToDoDto dto = new ToDoDto(name, description, begin, begin.plusYears(100));
        ToDo toDo = ToDo.createTask(dto);

        assertThat(toDo).isNotNull();
        assertThat(toDo.getName()).isEqualTo(name);
        assertThat(toDo.getDescription()).isEqualTo(description);
        assertThat(toDo.getEnrollmentDateTime()).isEqualTo(begin);
        assertThat(toDo.getToDoStatus()).isEqualTo(ToDoStatus.TODO);

    }

    private static Stream<Arguments> testParams() {

        LocalDateTime now = LocalDateTime.of(2023,3,14,18,00);

        return Stream.of(
                Arguments.of("test1", "test1", now),
                Arguments.of("test2", "test2", now.plusDays(10)),
                Arguments.of("test3", "test3", now.plusYears(10).plusWeeks(2))
        );

    }
}