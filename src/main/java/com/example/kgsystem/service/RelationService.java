package com.example.kgsystem.service;

import com.example.kgsystem.model.neo4j.RelationPath;

import java.util.List;

public interface RelationService {
    public Integer getCooperateNum(String firstName, String lastName);
    public List<RelationPath> getMultiJumpRelation(String startType,String startNode,String endType,String endNode,int minJump,int maxJump);
}
