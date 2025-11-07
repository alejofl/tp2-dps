package com.rt.springboot.app.adapter.driven.invoice;

import com.rt.springboot.app.adapter.driven.product.ProductRelationalEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "invoice_items")
public class InvoiceItemRelationalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int amount;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductRelationalEntity product;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceRelationalEntity invoice;
}
