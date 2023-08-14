package com.example.kgsystem.service.impl;

import com.example.kgsystem.mapper.UploadMapper;
import com.example.kgsystem.model.neo4j.GraphPostDto;
import com.example.kgsystem.model.neo4j.PointDto;
import com.example.kgsystem.service.UploadService;
import com.example.kgsystem.util.DatabaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service("UploadService")
@Slf4j
public class UploadServiceImpl implements UploadService {
    public final UploadMapper uploadMapper;

    @Autowired
    public UploadServiceImpl(){
        SqlSession session = DatabaseUtils.getSession("neo4j");
        this.uploadMapper = session.getMapper(UploadMapper.class);
    }

    @Override
    public List<Record> addGraph(GraphPostDto graphPostDto) {
        final Driver driver;
        AuthToken token = AuthTokens.basic("neo4j", "tsinghua2022");
        driver = GraphDatabase.driver("bolt://localhost:7687", token);
        try(Session session = driver.session()){
            session.writeTransaction(transaction -> {
                String s = "";
                if (graphPostDto.getComponent() != ""){
                    s += "merge (c:Component{name:'" + graphPostDto.getComponent() + "'})";
                }
                if (graphPostDto.getSituation() != ""){
                    s += "merge (s:Situation{name:'" + graphPostDto.getSituation() + "'})";
                }
                if (graphPostDto.getSolution() != ""){
                    s += "merge (m:Method{name:'" + graphPostDto.getSolution() + "'})";
                }
                if (graphPostDto.getEffect() != ""){
                    s += "merge (e:Effect{name:'" + graphPostDto.getEffect() + "'})";
                }
                if (graphPostDto.getDetection() != ""){
                    s += "merge (d:Detection{name:'" + graphPostDto.getDetection() + "'})";
                }
                if (graphPostDto.getReason() != ""){
                    s += "merge (r:Reason{name:'" + graphPostDto.getReason() + "'})";
                }
                if (graphPostDto.getPoint().get(0) != ""){
                    List<String> pointList = graphPostDto.getPoint();
                    for (int i = 0; i < pointList.size(); i++) {
                        s += "merge (p" + String.valueOf(i) + ":Point{name:'" + pointList.get(i) + "'})";
                    }
                }
                if (graphPostDto.getSituation() != "" && graphPostDto.getEffect() != ""){
                    s += "merge (s)-[r1:affect]->(e)";
                }
                if (graphPostDto.getReason() != "" && graphPostDto.getComponent() != ""){
                    s += "merge (c)-[r2:located]->(r)";
                }
                if (graphPostDto.getSituation() != "" && graphPostDto.getComponent() != ""){
                    s += "merge (c)-[r3:hasproblem]->(s)";
                }
                if (graphPostDto.getSituation() != "" && graphPostDto.getReason() != ""){
                    s += "merge (r)-[r4:cause]->(s)";
                }
                if (graphPostDto.getReason() != "" && graphPostDto.getSolution() != ""){
                    s += "merge (r)-[r5:advice]->(m)";
                }
                if (graphPostDto.getSituation() != "" && graphPostDto.getDetection() != ""){
                    s += "merge (s)-[r6:detected]->(d)";
                }
                if (graphPostDto.getSituation() != "" && graphPostDto.getPoint().get(0) != ""){
                    List<String> pointList = graphPostDto.getPoint();
                    for (int i = 0; i < pointList.size(); i++) {
                        s += "merge (s)-[r" + String.valueOf(i + 7) + ":measured]->(p" + String.valueOf(i) + ")";
                    }
                }
                Result result = transaction.run(s);
                return null;
            });
        }
        finally{
            driver.close();
        }
        //System.out.println(uploadMapper.addUploadGraph(graphPostDto));
        return null;
    }

    @Override
    public List<Record> addPoint(PointDto pointDto) {
        final Driver driver;
        AuthToken token = AuthTokens.basic("neo4j", "tsinghua2022");
        driver = GraphDatabase.driver("bolt://localhost:7687", token);
        try(Session session = driver.session()){
            session.writeTransaction(transaction -> {
                String s = "";
                if (pointDto.getId() != ""){
                    s += "merge (p:Point{name:'" + pointDto.getId() + "'})";
                }
                if (pointDto.getName() != ""){
                    s += "set p.description='" + pointDto.getName() + "'";
                }
                if (pointDto.getLowAlarm() != ""){
                    s += "set p.lowAlarm='" + pointDto.getLowAlarm() + "'";
                }
                if (pointDto.getHighAlarm() != ""){
                    s += "set p.highAlarm='" + pointDto.getHighAlarm() + "'";
                }
                if (pointDto.getHighhighAlarm() != ""){
                    s += "set p.highhighAlarm='" + pointDto.getHighhighAlarm() + "'";
                }
                if (pointDto.getNote() != ""){
                    s += "set p.note='" + pointDto.getNote() + "'";
                }
                Result result = transaction.run(s);
                return null;
            });
        }
        finally{
            driver.close();
        }
        //System.out.println(uploadMapper.addUploadGraph(graphPostDto));
        return null;
    }

    @Override
    public void addByExcelFile(MultipartFile excelFile) {
        GraphPostDto p = new GraphPostDto();
        try {
            InputStream is = excelFile.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            //for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                log.info("row={}", row);
                p.setComponent(row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setSituation(row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setReason(row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setSolution(row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                log.info("s={}",row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                log.info("s={}",row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
                log.info("s={}",row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
                p.setEffect(row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setDetection(row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                List<String> pointList = Arrays.asList(row.getCell(6,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().split("，"));
                p.setPoint(pointList);
                //p.setSolution(row.getCell(6).getStringCellValue());

                addGraph(p);
                log.info("graph={}", p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void addByPointExcelFile(MultipartFile excelFile) {
        PointDto p = new PointDto();
        try {
            InputStream is = excelFile.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Row titleRow = sheet.getRow(0);
            log.info("pointdsad={}", sheet.getLastRowNum());
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            //for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                log.info("row={}", row);
                p.setName(row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setId(row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setLowAlarm(row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setHighAlarm(row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setHighhighAlarm(row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                p.setNote(row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());

                log.info("point={}", p);
                addPoint(p);
                log.info("point={}", p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
