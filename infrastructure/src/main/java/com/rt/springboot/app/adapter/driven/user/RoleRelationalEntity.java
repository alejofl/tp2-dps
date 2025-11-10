package com.rt.springboot.app.adapter.driven.user;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class RoleRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String authority;
}
