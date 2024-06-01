package com.example.kunuz.dto.profile;

import com.example.kunuz.enums.ProfileRole;
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
