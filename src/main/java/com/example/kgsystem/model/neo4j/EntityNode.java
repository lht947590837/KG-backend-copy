package com.example.kgsystem.model.neo4j;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EntityNode {
    private String id;
    private String name;
    private String category;
    private String relation;
    //开始点
    private String point;


}
