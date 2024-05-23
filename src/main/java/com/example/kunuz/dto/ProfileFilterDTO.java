package com.example.kunuz.dto;

import com.example.kunuz.entity.AttachmentEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDateTime createdDateTo;
    private LocalDateTime createdDateFrom;
}
