package com.example.kunuz.dto.article;

import com.example.kunuz.entity.*;
import com.example.kunuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private long sharedCount;
    private AttachmentEntity image;
    private RegionEntity region;
    private CategoryEntity category;
    private ProfileEntity moderator;
    private ProfileEntity publisher;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private Boolean visible;
    private long viewCount;
    private List<TypesEntity> types;
}
