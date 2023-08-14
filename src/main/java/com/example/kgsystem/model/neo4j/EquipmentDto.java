package com.example.kgsystem.model.neo4j;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EquipmentDto {
    private String effect;

    private String detection;

    private String component;

    private String situation;

    private String reason;

    private String solution;
}
