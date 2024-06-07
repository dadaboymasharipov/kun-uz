package com.example.kunuz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attachment")
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
