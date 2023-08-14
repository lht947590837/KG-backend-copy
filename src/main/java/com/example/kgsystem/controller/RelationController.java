package com.example.kgsystem.controller;

import com.example.kgsystem.model.neo4j.RelationPath;
import com.example.kgsystem.service.RelationService;
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
@RequestMapping("api/Relation")
public class RelationController {
    @Qualifier("RelationService")
    private final RelationService relationService;

    @Autowired
    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping(value = "/multiJump")
    public ResponseEntity<?> getMultiJumpRelation(@RequestParam(value = "startType") String startType,
                                                  @RequestParam(value = "startNode") String startNode,
                                                  @RequestParam(value = "endType") String endType,
                                                  @RequestParam(value = "endNode") String endNode,
                                                  @RequestParam(value = "minJump", required = false) int minJump,
                                                  @RequestParam(value = "maxJump", required = false) int maxJump){
        List<RelationPath> relationPathList = relationService.getMultiJumpRelation(startType, startNode, endType, endNode, minJump, maxJump);
        if(relationPathList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(relationPathList, HttpStatus.OK);
    }

//    @GetMapping(value = "/cooperate")
//    public ResponseEntity<?> getCooperateNum(@RequestParam(value = "firstName") String firstName,
//                                             @RequestParam(value = "lastName") String lastName){
//        Integer num = relationService.getCooperateNum(firstName, lastName);
//        return new ResponseEntity<>(Objects.requireNonNullElse(num, 0), HttpStatus.OK);
//    }
}
