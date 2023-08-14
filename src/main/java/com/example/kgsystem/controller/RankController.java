package com.example.kgsystem.controller;

import com.example.kgsystem.model.neo4j.EquipmentDto;
import com.example.kgsystem.model.neo4j.SituationDto;
import com.example.kgsystem.model.neo4j.SolutionDto;
import com.example.kgsystem.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/Rank")
public class RankController {
    @Qualifier("RankService")
    private final RankService rankService;

    @Autowired
    public RankController(RankService rankService){
        this.rankService = rankService;
    }

    @GetMapping(value = "/equipment")
    public ResponseEntity<?> getEquipment(@RequestParam(value = "componentName") String componentName){
        System.out.println(componentName);
        List<EquipmentDto> equipmentList = rankService.getEquipment(componentName);
        if(equipmentList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
    }

    @GetMapping(value = "/situation")
    public ResponseEntity<?> getSituation(@RequestParam(value = "componentName") String componentName){
        List<SituationDto> situationDtoList = rankService.getSituation(componentName);
        if(situationDtoList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(situationDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/solution")
    public ResponseEntity<?> getInterestImportantPublication(@RequestParam(value = "componentName") String componentName){
        List<SolutionDto> solutionDtoList = rankService.getSolution(componentName);
        if(solutionDtoList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(solutionDtoList, HttpStatus.OK);
    }
}
