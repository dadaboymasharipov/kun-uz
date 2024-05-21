package com.example.kunuz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type")
public class TypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNumber;
}
