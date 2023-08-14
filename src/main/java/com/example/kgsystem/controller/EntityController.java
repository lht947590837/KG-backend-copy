package com.example.kgsystem.controller;


import com.example.kgsystem.model.neo4j.*;
import com.example.kgsystem.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/Entity")
public class EntityController {
    @Qualifier("entityService")
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/graph")
    public ResponseEntity<EntityDataDto> selectByName(@RequestParam(value = "name") String name, @RequestParam(value = "category") String category) {
        System.out.println(name);
        List<String> li=entityService.selectEntityId(new QuestDto(name,category));
        if(li.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        EntityDataDto l = entityService.getEntityData(li.get(0),name,category);
        if(l==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @GetMapping(value = "/message")
    public ResponseEntity<PointDto> getEntityMessage(@RequestParam(value = "name") String name, @RequestParam(value = "category") String category) {
        System.out.println(name);
        List<String> li=entityService.selectEntityId(new QuestDto(name,category));
        if(li.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        PointDto l = entityService.getEntityMessage(li.get(0),name,category);
        if(l==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(l, HttpStatus.OK);
    }
}
