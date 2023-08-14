package com.example.kgsystem.model.neo4j;

import com.example.kgsystem.model.neo4j.RelationNode;
import lombok.Data;

import java.util.List;

@Data
public class RelationPath {
    List<RelationNode> node;
    List<String> relationship;
    int length;
}
