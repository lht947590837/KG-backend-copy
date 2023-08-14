package com.example.kgsystem.service;

import com.example.kgsystem.model.neo4j.GraphPostDto;
import com.example.kgsystem.model.neo4j.PointDto;
import org.neo4j.driver.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    public void addByExcelFile(MultipartFile excelFile);
    public void addByPointExcelFile(MultipartFile excelFile);
    public List<Record> addGraph(GraphPostDto graphPostDto);
    public List<Record> addPoint(PointDto pointDto);
}
