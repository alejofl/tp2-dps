package com.rt.springboot.app.adapter.driving.view.invoice;


import java.awt.Color;
import java.util.Map;

import com.rt.springboot.app.adapter.driving.dto.InvoiceDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("invoice/view")
public class InvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document,
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response
	) {

		final var invoice = (InvoiceDto) model.get("invoice");
		
		MessageSourceAccessor message = getMessageSourceAccessor();

		// Detalles del Cliente
		final var table = new PdfPTable(1);
		table.setSpacingAfter(20);
		final var cell = new PdfPCell(new Phrase(message.getMessage("text.factura.ver.datos.cliente")));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		table.addCell(cell);
		table.addCell(invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName());
		table.addCell(invoice.getClient().getEmail());
		
		// Detalles de Factura
		final var table2 = new PdfPTable(1);
		table2.setSpacingAfter(20);
		final var cell2 = new PdfPCell(new Phrase(message.getMessage("text.factura.ver.datos.factura")));
		cell2.setBackgroundColor(new Color(195, 230, 203));
		cell2.setPadding(8f);
		table2.addCell(cell2);
		table2.addCell(message.getMessage("text.cliente.factura.folio")+ ": " + invoice.getId());
		table2.addCell(message.getMessage("text.cliente.factura.descripcion")+ ": " + invoice.getDescription());
		table2.addCell(message.getMessage("text.cliente.factura.fecha")+ ": " + invoice.getCreatedAt());
	
		// Tabla de Productos
		final var table3 = new PdfPTable(4);
		table3.setWidths(new float[] {3.5f, 1, 1, 1});
		table3.addCell(message.getMessage("text.factura.form.item.nombre"));
		table3.addCell(message.getMessage("text.factura.form.item.precio"));
		table3.addCell(message.getMessage("text.factura.form.item.cantidad"));
		table3.addCell(message.getMessage("text.factura.form.item.total"));

		invoice.getItems().forEach(item -> {
			table3.addCell(item.getProduct().getName());
			table3.addCell(item.getProduct().getPrice());
			final var itemCell = new PdfPCell(new Phrase(String.format("%d", item.getAmount())));
			itemCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			table3.addCell(itemCell);
			table3.addCell(item.getSubtotal());
		});
		
		final var cell4 = new PdfPCell(new Phrase(message.getMessage("text.factura.form.total")));
		cell4.setColspan(3);
		cell4.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		table3.addCell(cell4);
		table3.addCell(invoice.getTotal());
		
		document.add(table);
		document.add(table2);
		document.add(table3);
	}
	
	
	
}
