package com.example.kgsystem.mapper;

import com.example.kgsystem.model.neo4j.GraphPostDto;
//import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadMapper {
    int addUploadGraph(GraphPostDto graphPostDto);
}
