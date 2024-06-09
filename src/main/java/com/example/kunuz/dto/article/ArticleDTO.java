package com.example.kunuz.dto.article;

import com.example.kunuz.entity.AttachmentEntity;
import com.example.kunuz.entity.TypesEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    //    private RegionEntity region;
//    private CategoryEntity category;
//    private ProfileEntity moderator;
//    private ProfileEntity publisher;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

    private long viewCount;
    private List<Integer> types;
}
