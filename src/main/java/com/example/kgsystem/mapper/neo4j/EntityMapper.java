package com.example.kgsystem.mapper.neo4j;


import com.example.kgsystem.model.neo4j.EntityNode;
import com.example.kgsystem.model.neo4j.PointDto;
import com.example.kgsystem.model.neo4j.QuestDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EntityMapper {

    @Select("MATCH (n:Point) where id(n)=#{name} \n" +
            "RETURN n.description as name,n.name as id,n.lowAlarm as lowAlarm,n.highAlarm as highAlarm,n.highhighAlarm as highhighAlarm,n.note as note")
    PointDto getPointMessage(Integer name);

    @Select("MATCH (n:Component)-[r:located|hasproblem]-(n2)  where id(n)=#{name} \n" +
            "return  id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(startnode(r)) as point\n")
    List<EntityNode> getComponent(Integer name);

    @Select("MATCH (n:Detection)-[r:detected]-(n2) where id(n)=#{name} \n" +
            "return  id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n) as point\n" +
            "\n")
    List<EntityNode> getDetection(Integer name);

    @Select("MATCH (n:Effect)-[r:affect]-(n2) where id(n)=#{name} \n" +
        "return id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n2) as point\n")
    List<EntityNode> getEffect(Integer name);

    @Select("MATCH (n:Method)-[r:advice]-(n2) where id(n)=#{name} \n" +
            "return id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n2) as point\n")
    List<EntityNode> getMethod(Integer name);

    @Select("MATCH (n:Point)-[r:measured]-(n2) where id(n)=#{name} \n" +
            "return id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n2) as point\n")
    List<EntityNode> getPoint(Integer name);

    @Select("MATCH (n:Reason)-[r:cause|located|advice]-(n2) where id(n)=#{name} \n" +
            "return id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n) as point")
    List<EntityNode> getReason(Integer name);

    @Select("MATCH (n:Situation)-[r:measured|detected|affect|cause|hasproblem]-(n2) where id(n)=#{name} \n" +
            "return id(n2) as id, n2.name as name,labels(n2)[0] as category , TYPE(r) as relation,id(n2) as point\n")
    List<EntityNode> getSituation(Integer name);


    @Select("MATCH (p:Component )where p.name=#{name}  RETURN id(p) ")
    List<String> selectComponentId(QuestDto questDto);
    @Select("MATCH (p:Detection )where p.name=#{name} RETURN id(p)")
    List<String> selectDetectionId(QuestDto questDto);
    @Select("MATCH (p:Effect )where p.name=#{name} RETURN id(p)")
    List<String> selectEffectId(QuestDto questDto);
    @Select("MATCH (p:Method )where p.name=#{name} RETURN id(p)")
    List<String> selectMethodId(QuestDto questDto);
    @Select("MATCH (p:Point )where p.name=#{name} RETURN id(p)")
    List<String> selectPointId(QuestDto questDto);
    @Select("MATCH (p:Reason )where p.name=#{name} RETURN id(p)")
    List<String> selectReasonId(QuestDto questDto);
    @Select("MATCH (p:Situation )where p.name=#{name} RETURN id(p)")
    List<String> selectSituationId(QuestDto questDto);

}
