package com.example.bean.controller;

import com.example.bean.model.Image;
import com.example.bean.model.response.FileResponse;
import com.example.bean.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @Autowired
    private FileService service;

    // 1. Upload file
    @PostMapping("files")
    public ResponseEntity<?> uploadFile(@ModelAttribute("file")MultipartFile file) {
        FileResponse fileResponse = service.uploadFile(file);
        return ResponseEntity.ok(fileResponse);
    }


    // 2. xem thông tin file
    @GetMapping(value = "files/{id}")
    public ResponseEntity<?> readFile(@PathVariable Integer id) {
        Image image = service.readFile(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());
    }

}
