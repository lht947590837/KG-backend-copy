package com.example.kgsystem.service.impl;

import com.example.kgsystem.model.neo4j.*;
import com.example.kgsystem.service.EntityService;
import com.example.kgsystem.mapper.neo4j.EntityMapper;
import com.example.kgsystem.util.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("EntityService")
@Slf4j
public class EntityServiceImpl implements EntityService {

    private final EntityMapper entityMapper;
    private final ModelMapper modelMapper;
    static  List<EntityDataDto.Category> categories=new ArrayList<>();

    @Autowired
    public EntityServiceImpl(ModelMapper modelMapper) {

        SqlSession session = DatabaseUtils.getSession("neo4j");
        this.entityMapper = session.getMapper(EntityMapper.class);
        this.modelMapper = modelMapper;
        categories.add(new EntityDataDto.Category("Component"));
        categories.add(new EntityDataDto.Category("Detection"));
        categories.add(new EntityDataDto.Category("Effect"));
        categories.add(new EntityDataDto.Category("Method"));
        categories.add(new EntityDataDto.Category("Point"));
        categories.add(new EntityDataDto.Category("Reason"));
        categories.add(new EntityDataDto.Category("Situation"));
    }


    @Override
    public PointDto getEntityMessage(String id, String name, String category) {
        Integer temp=Integer.parseInt(id);
        return entityMapper.getPointMessage(temp);
    }

    @Override
    public EntityDataDto getEntityData(String id, String name, String category) {
        List<EntityNode> l;
        Integer temp=Integer.parseInt(id);
        switch (category){
            case "Component":
                l=entityMapper.getComponent(temp);
                break;
            case "Detection":
                l=entityMapper.getDetection(temp);
                break;
            case "Effect":
                l=entityMapper.getEffect(temp);
                break;
            case "Method":
                l=entityMapper.getMethod(temp);
                break;
            case "Point":
                l=entityMapper.getPoint(temp);
                break;
            case "Reason":
                l=entityMapper.getReason(temp);
                break;
            case "Situation":
                l=entityMapper.getSituation(temp);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        EntityDataDto e=new EntityDataDto();
        List<EntityDataDto.Node> nodes=new ArrayList<>();
        List<EntityDataDto.Link> links=new ArrayList<>();


        nodes.add(new EntityDataDto.Node(id,name,category));
        for(EntityNode entityNode :l){
            nodes.add(new EntityDataDto.Node(entityNode.getId(),entityNode.getName(),entityNode.getCategory()));
            if(entityNode.getPoint().equals(id)){
                links.add(new EntityDataDto.Link(id,entityNode.getId(),entityNode.getRelation()));
            }else{
                links.add(new EntityDataDto.Link(entityNode.getId(),id,entityNode.getRelation()));
            }

        }
        e.setNodes(nodes);
        e.setLinks(links);
        e.setCategories(categories);
        return e;

    }

    @Override
    public List<String> selectEntityId(QuestDto questDto) {
        switch (questDto.getCategory()){
            case "Component":
                return entityMapper.selectComponentId(questDto);
            case "Detection":
                return entityMapper.selectDetectionId(questDto);
            case "Effect":
                return entityMapper.selectEffectId(questDto);
            case "Method":
                return entityMapper.selectMethodId(questDto);
            case "Point":
                return entityMapper.selectPointId(questDto);
            case "Reason":
                return entityMapper.selectReasonId(questDto);
            case "Situation":
                return entityMapper.selectSituationId(questDto);
            default:
                throw new IllegalStateException("Unexpected value: "+questDto);
        }

    }


}
