package com.example.kunuz.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdateDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "surname cannot be blank")
    private String surname;
}
