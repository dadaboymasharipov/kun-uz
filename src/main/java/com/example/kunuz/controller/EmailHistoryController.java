package com.example.kunuz.controller;

import com.example.kunuz.dto.emailHistory.EmailHistoryDTO;
import com.example.kunuz.repository.EmailHistoryRepository;
import com.example.kunuz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/email/history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

//    @GetMapping("/byEmail")
//    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam("email") String email) {
//        List<EmailHistoryDTO> emails = emailHistoryService.(email);
//        return ResponseEntity.ok(emails);
//    }


}
