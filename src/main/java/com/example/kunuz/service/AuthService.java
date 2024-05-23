package com.example.kunuz.service;

import com.example.kunuz.dto.auth.RegistrationDTO;
import com.example.kunuz.entity.EmailHistoryEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.EmailHistoryRepository;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);

        // send email
        StringBuilder message = new StringBuilder();
        message.append("Click to the link to complete registration \n");
        message.append("http://localhost:8080/auth/verification/");
        message.append(entity.getId());
        message.append("\n Mazgi.");

        mailSenderService.send(dto.getEmail(),
                "Complete registration",
                message.toString());

        EmailHistoryEntity emailHistory = new EmailHistoryEntity();
        emailHistory.setEmail(dto.getEmail());
        emailHistory.setMessage(message.toString());

        emailHistoryRepository.save(emailHistory);
        return "To complete your registration please verify your email.";

    }

    public String authorizationVerification(String userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);

        return "Success";
    }


}
