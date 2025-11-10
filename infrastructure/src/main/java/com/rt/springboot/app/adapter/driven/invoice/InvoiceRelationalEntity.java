package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.client.ClientRelationalEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "invoices")
public class InvoiceRelationalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Column
    private String description;

    @Column
    private String observation;

    @Column
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientRelationalEntity client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItemRelationalEntity> items;
}
