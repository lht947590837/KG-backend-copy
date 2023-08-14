package com.example.kgsystem.model.neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EntityDataDto {
    List<Node> nodes;
    List<Link> links;
    List<Category> categories;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node{
        String id;
        String name;
        String category;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Link{
        String source;
        String target;
        String relation;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category{
        String name;

    }

}
