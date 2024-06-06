package com.example.kunuz.service;

import com.example.kunuz.dto.auth.LoginDTO;
import com.example.kunuz.dto.auth.RegistrationDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;

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

        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        sendRegistrationEmail(entity.getId(), dto.getEmail());

        return "To complete your registration please verify your email.";
    }

    public String authorizationVerification(String userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();

        emailHistoryService.isNotExpiredEmail(entity.getEmail());

        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    public String registrationResend(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();

        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration has already been completed");
        }
        emailHistoryService.checkEmailLimit(email);
        sendRegistrationEmail(entity.getId(), email);
        return "To complete your registration please verify your email.";
    }

    public void sendRegistrationEmail(String profileId, String email) {
        // send email
        String url = "http://localhost:8080/auth/verification/" + profileId;
        String formatText = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        a:link, a:visited {\n" +
                "            background-color: #f44336;\n" +
                "            color: white;\n" +
                "            padding: 14px 25px;\n" +
                "            text-align: center;\n" +
                "            text-decoration: none;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "\n" +
                "        a:hover, a:active {\n" +
                "            background-color: red;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please click button below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.crete(email, text);
    }

    public ProfileDTO login(LoginDTO loginDTO) {
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndVisibleTrue(loginDTO.getLogin());

        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();

        if (!entity.getPassword().equals(MD5Util.getMD5(loginDTO.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("User is not active");
        }
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setToken(JwtUtil.encode(entity.getId(), entity.getEmail(), entity.getRole()));
        return dto;
    }
}