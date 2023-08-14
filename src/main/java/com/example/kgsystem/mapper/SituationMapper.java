package com.example.kgsystem.mapper;

import com.example.kgsystem.model.neo4j.SituationDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SituationMapper {

//    @Select("match(n:Situation)-[r:hasproblem]-(m:Component{name:#{componentName}})-[r1:located]-(p:Reason) ,r0=(n)-[]-(p) return n.name as situation,p.name as reason")
    List<SituationDto> getSituation(String componentName);
}