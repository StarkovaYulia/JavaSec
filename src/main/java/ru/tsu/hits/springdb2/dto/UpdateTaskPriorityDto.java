package ru.tsu.hits.springdb2.dto;

import lombok.Data;
import ru.tsu.hits.springdb2.entity.Priority;

@Data
public class UpdateTaskPriorityDto {
    private Priority priority;
}
