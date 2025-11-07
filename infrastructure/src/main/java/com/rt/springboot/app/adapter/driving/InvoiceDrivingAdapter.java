package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driving.invoice.DeleteInvoiceUseCase;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceDrivingAdapter {

    private final DeleteInvoiceUseCase deleteInvoiceUseCase;
    private final FindInvoiceUseCase findInvoiceUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable UUID id) {
        return ResponseEntity.ok(findInvoiceUseCase.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        deleteInvoiceUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
