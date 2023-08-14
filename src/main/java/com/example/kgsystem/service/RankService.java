package com.example.kgsystem.service;


import com.example.kgsystem.model.neo4j.EquipmentDto;
import com.example.kgsystem.model.neo4j.SituationDto;
import com.example.kgsystem.model.neo4j.SolutionDto;

import java.util.List;

public interface RankService {
    public List<EquipmentDto> getEquipment(String componentName);
    public List<SituationDto> getSituation(String componentName);
    public List<SolutionDto> getSolution(String componentName);
}
