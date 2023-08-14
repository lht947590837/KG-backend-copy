package com.example.kgsystem.service.impl;

import com.example.kgsystem.mapper.neo4j.RelationMapper;
import com.example.kgsystem.model.neo4j.RelationPath;
import com.example.kgsystem.model.neo4j.RelationNode;
import com.example.kgsystem.service.RelationService;
import com.example.kgsystem.util.DatabaseUtils;
import org.apache.ibatis.session.SqlSession;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("RelationService")
public class RelationServiceImpl implements RelationService{
    public final RelationMapper relationMapper;

    @Autowired
    public RelationServiceImpl(){
        SqlSession session = DatabaseUtils.getSession("neo4j");
        this.relationMapper = session.getMapper(RelationMapper.class);
    }

    @Override
    public Integer getCooperateNum(String firstName, String lastName){
        return relationMapper.getCooperateNum(firstName,lastName);
    }

    @Override
    public List<RelationPath> getMultiJumpRelation(String startType,String startNode,String endType,String endNode,int minJump,int maxJump){
        final Driver driver;
        List<RelationPath> pathList = new ArrayList<>();

        AuthToken token = AuthTokens.basic("neo4j", "tsinghua2022");
        driver = GraphDatabase.driver("bolt://localhost:7687", token);

        try(Session session = driver.session()){
            return session.readTransaction(transaction -> {
                String s = "";
                s = String.format("MATCH data=(n:%s{name:'%s'})-[*%d..%d]-(m:%s{name:'%s'}) return data",
                        startType, startNode, minJump, maxJump, endType, endNode);
                Result result = transaction.run(s);
                List<Record> recordList = result.list();
                for(Record record: recordList){
                    Path p = record.get("data").asPath();
                    RelationPath curPath = new RelationPath();
                    List<RelationNode> nodeList = new ArrayList<>();
                    List<String> relationshipList = new ArrayList<>();
                    System.out.println(p);
                    for(Node node: p.nodes()){
                        RelationNode relationNode = new RelationNode();
                        System.out.println(node.values().iterator());
                        Iterator<Value> iter = node.values().iterator();
                        Iterator<String> iterLabel = node.labels().iterator();
                        relationNode.setId(String.valueOf(node.id()));
                        String nodeLabel = iter.next().toString();
                        relationNode.setCategory(iterLabel.next());
                        relationNode.setName(nodeLabel.substring(1, nodeLabel.length()-1));
                        nodeList.add(relationNode);
                    }
                    for(Relationship relationship: p.relationships()){
                        System.out.println(relationship.type());
                        relationshipList.add(relationship.type());
                    }
                    curPath.setNode(nodeList);
                    curPath.setRelationship(relationshipList);
                    curPath.setLength(relationshipList.size());
                    pathList.add(curPath);
                }
                return pathList;
            });
        }
        finally{
            driver.close();
        }
    }
}
