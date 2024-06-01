package com.example.kunuz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "surname cannot be blank")
    private String surname;
    @NotBlank(message = "email cannot be blank")
    private String email;
//    @NotBlank(message = "phone cannot be blank")
    private String pone;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
