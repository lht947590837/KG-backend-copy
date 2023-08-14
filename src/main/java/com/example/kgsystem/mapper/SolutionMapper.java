package com.example.kgsystem.mapper;

import com.example.kgsystem.model.neo4j.SolutionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionMapper {

//    @Select("match(m:Component{name:#{componentName}})-[r1:located]-(p:Reason)-[r2:advice]-(n:Method)  return p.name as reason,n.name as solution")
    List<SolutionDto> getSolution(String componentName);
}