package com.example.kunuz.dto.jwt;

import com.example.kunuz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String id;
    private ProfileRole role;

    public JwtDTO(String id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }

    public JwtDTO(String id) {
        this.id = id;
    }
}
