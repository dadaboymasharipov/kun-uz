package com.example.kunuz.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
//    @NotNull(message = "moderator_id cannot be null")
//    private String moderatorId;//TODO: ask if this should be added
//    @NotNull(message = "publisher_id cannot be null")
    private String publisherId;
    @NotEmpty(message = "types cannot be empty")
    private Integer[] types;
}

