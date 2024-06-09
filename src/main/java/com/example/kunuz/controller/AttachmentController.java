package com.example.kunuz.controller;

import com.example.kunuz.dto.attach.AttachDTO;
import com.example.kunuz.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /*@PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = attachmentService.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }*/

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO attachDTO = attachmentService.saveAttach(file);
        return ResponseEntity.ok().body(attachDTO);
    }
    
    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return this.attachmentService.load(fileName);
    }
}
