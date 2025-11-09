package com.rt.springboot.app.adapter.driven.attachment;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attachments")
public class AttachmentRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String filename;

    @Column
    private byte[] bytes;
}
