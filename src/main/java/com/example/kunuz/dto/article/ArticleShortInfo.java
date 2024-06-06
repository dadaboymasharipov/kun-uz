package com.example.kunuz.dto.article;

import com.example.kunuz.entity.AttachmentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ArticleShortInfo {
    private String id;
    private String title;
    private String description;
    private AttachmentEntity image;
    private LocalDateTime publishDate;
}
