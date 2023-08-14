package com.example.kgsystem.service.impl;

import com.example.kgsystem.mapper.EquipmentMapper;
import com.example.kgsystem.mapper.SituationMapper;
import com.example.kgsystem.mapper.SolutionMapper;
import com.example.kgsystem.model.neo4j.EquipmentDto;
import com.example.kgsystem.model.neo4j.SituationDto;
import com.example.kgsystem.model.neo4j.SolutionDto;
import com.example.kgsystem.service.RankService;
import com.example.kgsystem.util.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RankService")
@Slf4j
public class RankServiceImpl implements RankService {
    public final EquipmentMapper equipmentMapper;
    public final SolutionMapper solutionMapper;
    public final SituationMapper situationMapper;

    @Autowired
    public RankServiceImpl(){
        SqlSession session = DatabaseUtils.getSession("neo4j");
        this.equipmentMapper = session.getMapper(EquipmentMapper.class);
        this.solutionMapper = session.getMapper(SolutionMapper.class);
        this.situationMapper = session.getMapper(SituationMapper.class);
    }

    @Override
    public List<EquipmentDto> getEquipment(String componentName){
        List<EquipmentDto> equipmentList = equipmentMapper.getEquipment(componentName);;
        return equipmentList;
    }

    @Override
    public List<SituationDto> getSituation(String componentName){
        List<SituationDto> situationList = situationMapper.getSituation(componentName);
        return situationList;
    }

    @Override
    public List<SolutionDto> getSolution(String componentName){
        List<SolutionDto> solutionList = solutionMapper.getSolution(componentName);
        return solutionList;
    }
}
