package com.rt.springboot.app.adapter.driving.view.invoice;

import java.util.Map;
import java.util.stream.IntStream;

import com.rt.springboot.app.adapter.driving.dto.InvoiceDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Component("invoice/view.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response
	) {

		response.setHeader("Content-Disposition", "attachment; filename=\"invoice_view.xlsx\"");

		MessageSourceAccessor message = getMessageSourceAccessor();

		final var invoice = (InvoiceDto) model.get("invoice");
		final var sheet = workbook.createSheet("Factura Spring");
				
		sheet.createRow(0).createCell(0).setCellValue(message.getMessage("text.factura.ver.datos.cliente"));
		sheet.createRow(1).createCell(0).setCellValue(invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName());
		sheet.createRow(2).createCell(0).setCellValue(invoice.getClient().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(message.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(message.getMessage("text.cliente.factura.folio")+ ": " + invoice.getId());
		sheet.createRow(6).createCell(0).setCellValue(message.getMessage("text.cliente.factura.descripcion")+ ": " + invoice.getDescription());
		sheet.createRow(7).createCell(0).setCellValue(message.getMessage("text.cliente.factura.fecha")+ ": " + invoice.getCreatedAt());
		
		final var theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		final var tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		final var header = sheet.createRow(9);
		header.createCell(0).setCellValue(message.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(message.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(message.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(message.getMessage("text.factura.form.item.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);

		IntStream.range(0, invoice.getItems().size())
				.forEach(i -> {
					final var item = invoice.getItems().get(i);

					final var row = sheet.createRow(i + 10);

					final var cell = row.createCell(0);
					cell.setCellValue(item.getProduct().getName());
					cell.setCellStyle(tbodyStyle);

					final var cell1 = row.createCell(1);
					cell1.setCellValue(item.getProduct().getPrice());
					cell1.setCellStyle(tbodyStyle);

					final var cell2 = row.createCell(2);
					cell2.setCellValue(item.getAmount());
					cell2.setCellStyle(tbodyStyle);

					final var cell3 = row.createCell(3);
					cell3.setCellValue(item.getSubtotal());
					cell3.setCellStyle(tbodyStyle);
				});

		
		final var finalRow = sheet.createRow(invoice.getItems().size() + 11);
		final var finalCellTitle = finalRow.createCell(2);
		finalCellTitle.setCellValue(message.getMessage("text.factura.form.total"));
		finalCellTitle.setCellStyle(tbodyStyle);
		
		final var finalCellValue = finalRow.createCell(3);
		finalCellValue.setCellValue(invoice.getTotal());
		finalCellValue.setCellStyle(tbodyStyle);
		
	}

}
