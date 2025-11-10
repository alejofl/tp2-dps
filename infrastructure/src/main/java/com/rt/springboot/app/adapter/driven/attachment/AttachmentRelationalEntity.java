package com.rt.springboot.app.adapter.driven.attachment;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "attachments")
public class AttachmentRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String filename;

    @Lob
    @Column(name = "bytes", columnDefinition = "BLOB")
    private byte[] bytes;
}
