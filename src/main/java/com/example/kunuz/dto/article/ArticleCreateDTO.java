package com.example.kunuz.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleCreateDTO {
    @NotBlank(message = "title cannot be blank")
    private String title;
    @NotBlank(message = "description cannot be blank")
    private String description;
    @NotBlank(message = "content cannot be blank")
    private String content;
    private long sharedCount;
    @NotNull(message = "image cannot be null")
    private Integer imageId;
    @NotNull(message = "region_id cannot be null")
    private Integer regionId;
    @NotNull(message = "category_id cannot be null")
    private Integer categoryId;
    @NotEmpty(message = "types cannot be empty")
    private List<Integer> types;
}

