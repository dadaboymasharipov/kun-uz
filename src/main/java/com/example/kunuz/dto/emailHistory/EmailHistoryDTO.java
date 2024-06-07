package com.example.kunuz.dto.emailHistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private String id;
    private String message;
    @Email(message = "Please enter a valid email")
    private String email;
    private LocalDateTime createdDate;
}