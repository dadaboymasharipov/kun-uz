package com.example.kunuz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "artice_like")
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
