package com.example.kunuz.entity;

import com.example.kunuz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @Column(name = "region_id")
    private Integer regionId;

    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @Column(name = "category_id")
    private Integer categoryId;

    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity moderator;

    @Column(name = "moderator_id")
    private String moderatorId;

    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;

    @Column(name = "publisher_id")
    private String publisherId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "view_count")
    private long viewCount;
}
