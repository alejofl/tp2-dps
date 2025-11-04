package com.rt.springboot.app.adapters.driven.invoice;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "invoice_items")
public class InvoiceItemRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int amount;

    @Column
    private ProductRelationalEntity product;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceRelationalEntity invoice;
}
