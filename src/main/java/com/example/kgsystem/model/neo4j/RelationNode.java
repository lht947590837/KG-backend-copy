package com.example.kgsystem.model.neo4j;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelationNode {
    private String id;
    private String name;
    private String category;
}
