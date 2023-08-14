package com.example.kgsystem.model.neo4j;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolutionDto {
    private String reason;

    private String solution;
}
