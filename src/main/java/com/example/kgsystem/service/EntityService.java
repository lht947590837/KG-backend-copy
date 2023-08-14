package com.example.kgsystem.service;

import com.example.kgsystem.model.neo4j.*;

import java.util.List;


public interface EntityService {

    EntityDataDto getEntityData(String id, String name, String category);
    PointDto getEntityMessage(String id, String name, String category);

    List<String> selectEntityId(QuestDto questDto);
}
