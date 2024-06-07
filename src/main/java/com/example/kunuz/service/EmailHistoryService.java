package com.example.kunuz.service;

import com.example.kunuz.dto.emailHistory.EmailHistoryDTO;
import com.example.kunuz.entity.EmailHistoryEntity;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;


    public void crete(String toEmail, String text) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toEmail);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public void checkEmailLimit(String email) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = emailHistoryRepository.countByEmailAndCreatedDateBetween(email, from, to);
        if (count >= 3) {
            throw new AppBadException("Email limit reached. Please try after some time");
        }
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional =
                emailHistoryRepository.findTopByEmailOrderByCreatedDateDesc(email);

        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }


    public List<EmailHistoryDTO> getByEmail(EmailHistoryDTO dto) {
        List<EmailHistoryEntity> emailHistoryByEmail =
                emailHistoryRepository.findAllByEmail(dto.getEmail());

        LinkedList<EmailHistoryDTO> result = new LinkedList<>();
        emailHistoryByEmail.forEach(emailHistory -> result.add(toDto(emailHistory)));
        return result;
    }
    

    public EmailHistoryDTO toDto(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(dto.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<EmailHistoryDTO> getByDate(EmailHistoryDTO dto) {
        List<EmailHistoryEntity> emailHistoryByEmail =
                emailHistoryRepository.findAllByCreatedDate(dto.getCreatedDate());
//        TODO: check if wrong format date is accepted or not
        LinkedList<EmailHistoryDTO> result = new LinkedList<>();
        emailHistoryByEmail.forEach(emailHistory -> result.add(toDto(emailHistory)));
        return result;
    }


    public Page<EmailHistoryDTO> pagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<EmailHistoryEntity> historyByPage = emailHistoryRepository.findAll(pageRequest);
        LinkedList<EmailHistoryDTO> emailHistory = new LinkedList<>();
        historyByPage.getContent().forEach(entity -> emailHistory.add(toDto(entity)));
        long totalCount = historyByPage.getTotalElements();
        return new PageImpl<>(emailHistory, pageRequest, totalCount);
    }
}





