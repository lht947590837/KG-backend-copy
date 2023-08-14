package com.example.kgsystem.mapper;

import com.example.kgsystem.model.neo4j.EquipmentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentMapper {

    List<EquipmentDto> getEquipment(String componentName);
}