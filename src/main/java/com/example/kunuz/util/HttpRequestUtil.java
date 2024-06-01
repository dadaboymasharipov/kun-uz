package com.example.kunuz.util;

import com.example.kunuz.dto.jwt.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exception.AppBadException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

    public static JwtDTO getJwtDTO(HttpServletRequest request) {
        String id = (String) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        return new JwtDTO(id, role);
    }

    public static JwtDTO getJwtDTO(HttpServletRequest request, ProfileRole requiredRole) {
        String id = (String) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");
        JwtDTO dto = new JwtDTO(id, role);

        if (!dto.getRole().equals(requiredRole)) {
            throw new AppBadException("Method not allowed");
        }
        return dto;
    }
}
