package com.crud.tasks.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrelloCardDto {
    private String name;
    private String description;
    private String position;
    private String listId;
}
