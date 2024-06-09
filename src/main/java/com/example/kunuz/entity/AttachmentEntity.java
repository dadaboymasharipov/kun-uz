package com.example.kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Getter
@Setter
public class AttachmentEntity {
    @Id
    private String id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "created_date")
    private LocalDateTime createdData = LocalDateTime.now();
}