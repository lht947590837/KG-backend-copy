package com.example.kgsystem.model.neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestDto {
    private String name;
    private String category;
}
