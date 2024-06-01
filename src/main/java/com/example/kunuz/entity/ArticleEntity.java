package com.example.kunuz.entity;

import com.example.kunuz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "title", columnDefinition = "text")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "content", columnDefinition = "text")
    private String content;
    @Column(name = "shared_count")
    private long sharedCount;
    @JoinColumn(name = "photo_id")
    @OneToOne(fetch = FetchType.LAZY)
    private AttachmentEntity image;
    @JoinColumn(name = "region_id")
    @OneToOne(fetch = FetchType.LAZY)
    private RegionEntity region;
    @JoinColumn(name = "category_id")
    @OneToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;
    @JoinColumn(name = "moderator_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity moderator;
    @JoinColumn(name = "publisher_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;
    @Enumerated
    @Column(name = "status")
    private ArticleStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "view_count")
    private long viewCount;
    @OneToMany
    @JoinColumn(name = "types")
    private List<TypesEntity> types;
}
