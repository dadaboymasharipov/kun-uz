package com.example.kunuz.dto;

import com.example.kunuz.entity.AttachmentEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate = LocalDateTime.now();
    private AttachmentEntity photo;
}
