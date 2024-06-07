package com.example.kunuz.controller;

import com.example.kunuz.dto.emailHistory.EmailHistoryDTO;
import com.example.kunuz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/byEmail")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(EmailHistoryDTO dto) {
        List<EmailHistoryDTO> emails = emailHistoryService.getByEmail(dto);
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<EmailHistoryDTO>> getByDate(EmailHistoryDTO dto) {
        List<EmailHistoryDTO> emails = emailHistoryService.getByDate(dto);
        return ResponseEntity.ok(emails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public Page<EmailHistoryDTO> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return emailHistoryService.pagination(page - 1, size);

    }


}
