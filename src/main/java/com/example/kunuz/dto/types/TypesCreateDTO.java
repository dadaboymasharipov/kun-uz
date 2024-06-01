package com.example.kunuz.dto.types;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypesCreateDTO {
    @NotNull(message = "orderNumber cannot be null")
    private Integer orderNumber;
    @NotBlank(message = "nameUz cannot be blank")
    private String nameUz;
    @NotBlank(message = "nameRu cannot be blank")
    private String nameRu;
    @NotBlank(message = "nameEn cannot be blank")
    private String nameEn;
}
