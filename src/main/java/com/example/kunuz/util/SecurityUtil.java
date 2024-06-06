package com.example.kunuz.util;

import com.example.kunuz.config.CustomUserDetails;
import com.example.kunuz.dto.jwt.JwtDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exception.AppBadException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static JwtDTO getJwtDTO(String token) {
        String jwt = token.substring(7); // Bearer eyJhb
        return JwtUtil.decode(jwt);
    }

    public static JwtDTO getJwtDTO(String token, ProfileRole requiredRole) {
        JwtDTO dto = getJwtDTO(token);
        if (!dto.getRole().equals(requiredRole)) {
            throw new AppBadException("Method not allowed");
        }
        return dto;
    }

    public static String getProfileId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getProfile().getId();
    }

    public static ProfileEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getProfile();
    }


}
