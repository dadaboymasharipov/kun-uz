package com.example.kunuz.dto;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCreateDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "surname cannot be blank")
    private String surname;
    @Email(message = "please enter valid email")
    private String email;
    @NotBlank(message = "phone cannot be blank")
    private String phone;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotBlank(message = "status cannot be blank")
    private ProfileStatus status;
    @NotBlank(message = "role cannot be blank")
    private ProfileRole role;
}
