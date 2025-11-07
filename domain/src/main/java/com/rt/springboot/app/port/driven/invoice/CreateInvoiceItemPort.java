package com.rt.springboot.app.port.driven.invoice;

import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.InvoiceItem;
import com.rt.springboot.app.model.Product;

public interface CreateInvoiceItemPort {

    InvoiceItem create(Invoice invoice, Product product, Integer amount);
}
