package com.example.kgsystem.mapper.neo4j;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationMapper {

    @Select("optional MATCH (n:author{name:#{firstName}})-[r:cooperate]-(m:author{name:#{lastName}}) RETURN r.`co-author_count`")
    Integer getCooperateNum(String firstName, String lastName);
}
