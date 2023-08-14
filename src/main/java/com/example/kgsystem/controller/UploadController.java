package com.example.kgsystem.controller;

import com.example.kgsystem.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("api/Upload")
public class UploadController {
    @Qualifier("UploadService")
    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(value = "/uploadGraph")
    public ResponseEntity<?> uploadGraph(@RequestParam("excelFile") MultipartFile excelFile){
        String name = excelFile.getOriginalFilename();
        log.info(name);
        uploadService.addByExcelFile(excelFile);
        if (name.length() < 6 || !".xls".equals(name.substring(name.length() - 5))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/uploadPoint")
    public ResponseEntity<?> uploadPoint(@RequestParam("excelFile") MultipartFile excelFile){
        String name = excelFile.getOriginalFilename();
        log.info(name);
        uploadService.addByPointExcelFile(excelFile);
        if (name.length() < 6 || !".xls".equals(name.substring(name.length() - 5))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
