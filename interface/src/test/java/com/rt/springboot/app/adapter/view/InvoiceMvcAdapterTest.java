package com.rt.springboot.app.adapter.view;

import com.rt.springboot.app.Pair;
import com.rt.springboot.app.adapter.driving.InvoiceMvcAdapter;
import com.rt.springboot.app.adapter.driving.dto.ClientDto;
import com.rt.springboot.app.adapter.driving.dto.CreateInvoiceDto;
import com.rt.springboot.app.adapter.driving.dto.ProductDto;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.model.Product;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceUseCase;
import com.rt.springboot.app.port.driving.invoice.DeleteInvoiceUseCase;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import com.rt.springboot.app.port.driving.product.FindProductsByNameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceMvcAdapterTest {

    @Mock private DeleteInvoiceUseCase deleteInvoiceUseCase;
    @Mock private FindInvoiceUseCase findInvoiceUseCase;
    @Mock private FindClientUseCase findClientUseCase;
    @Mock private CreateInvoiceUseCase createInvoiceUseCase;
    @Mock private FindProductsByNameUseCase findProductsByNameUseCase;
    @Mock private MessageSource messageSource;

    @Mock private Model model;
    @Mock private RedirectAttributes flash;
    @Mock private BindingResult mockBindingResult;

    @Mock private Client mockClient;
    @Mock private Invoice mockInvoice;
    @Mock private Product mockProduct;

    @Captor
    private ArgumentCaptor<List<Pair<UUID, Integer>>> itemsCaptor;

    private InvoiceMvcAdapter adapter;
    private Locale locale;
    private UUID testId;
    private UUID testClientId;

    @BeforeEach
    void setUp() {
        adapter = new InvoiceMvcAdapter(
                deleteInvoiceUseCase,
                findInvoiceUseCase,
                findClientUseCase,
                createInvoiceUseCase,
                findProductsByNameUseCase,
                messageSource
        );
        locale = Locale.ENGLISH;
        testId = UUID.randomUUID();
        testClientId = UUID.randomUUID();
    }


    @Test
    void testViewInvoiceFound() {
        String titleFormat = "Invoice Detail: %s";
        String invoiceDesc = "Test Invoice";

        when(findInvoiceUseCase.findById(testId)).thenReturn(mockInvoice);
        when(mockInvoice.description()).thenReturn(invoiceDesc);
        when(messageSource.getMessage(eq("text.factura.ver.titulo"), any(), eq(locale)))
                .thenReturn(titleFormat);

        String viewName = adapter.view(testId, model, flash, locale);

        assertEquals("invoice/view", viewName);
        verify(findInvoiceUseCase, times(1)).findById(testId);
        verify(model, times(1)).addAttribute(eq("invoice"), any()); // Mapper estático
        verify(model, times(1)).addAttribute("title", String.format(titleFormat, invoiceDesc));
        verifyNoInteractions(flash);
    }

    @Test
    void testViewInvoiceNotFound() {
        String errorMsg = "Invoice not found";
        when(findInvoiceUseCase.findById(testId)).thenReturn(null);
        when(messageSource.getMessage(eq("text.factura.flash.db.error"), any(), eq(locale)))
                .thenReturn(errorMsg);

        String viewName = adapter.view(testId, model, flash, locale);

        assertEquals("redirect:/list", viewName);
        verify(flash, times(1)).addAttribute("error", errorMsg);
        verifyNoInteractions(model);
    }


    @Test
    void testLoadProducts() {
        String term = "saxo";
        when(findProductsByNameUseCase.findByName(term)).thenReturn(List.of(mockProduct));

        List<ProductDto> result = adapter.loadProducts(term);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(findProductsByNameUseCase, times(1)).findByName(term);
    }

    @Test
    void testLoadProducts_Empty() {
        String term = "saxo";
        when(findProductsByNameUseCase.findByName(term)).thenReturn(Collections.emptyList());

        List<ProductDto> result = adapter.loadProducts(term);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    void testCreateClientFound() {
        String title = "New Invoice";
        when(findClientUseCase.findById(testClientId)).thenReturn(mockClient);
        when(messageSource.getMessage(eq("text.factura.form.titulo"), any(), eq(locale)))
                .thenReturn(title);

        String viewName = adapter.create(testClientId, model, flash, locale);

        assertEquals("invoice/form", viewName);
        verify(findClientUseCase, times(1)).findById(testClientId);
        verify(model, times(1)).addAttribute("title", title);
        verify(model, times(1)).addAttribute(eq("invoice"), any(CreateInvoiceDto.class));
        verifyNoInteractions(flash);
    }

    @Test
    void testCreateClientNotFound() {
        String errorMsg = "Client not found";
        when(findClientUseCase.findById(testClientId)).thenReturn(null);
        when(messageSource.getMessage(eq("text.cliente.flash.db.error"), any(), eq(locale)))
                .thenReturn(errorMsg);

        String viewName = adapter.create(testClientId, model, flash, locale);

        assertEquals("redirect:/list", viewName);
        verify(flash, times(1)).addAttribute("error", errorMsg);
        verifyNoInteractions(model);
    }


    @Test
    void testSaveValidationErrors() {
        String title = "New Invoice";
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(messageSource.getMessage(eq("text.factura.form.titulo"), any(), eq(locale)))
                .thenReturn(title);
        CreateInvoiceDto dto = new CreateInvoiceDto();

        String viewName = adapter.save(null, null, dto, mockBindingResult, model, flash, locale);

        assertEquals("invoice/form", viewName);
        verify(model, times(1)).addAttribute("title", title);
        verifyNoInteractions(flash);
        verifyNoInteractions(createInvoiceUseCase);
    }

    @Test
    void testSaveNoItemsError() {
        String title = "New Invoice";
        String errorMsg = "No items";
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(messageSource.getMessage(eq("text.factura.form.titulo"), any(), eq(locale)))
                .thenReturn(title);
        when(messageSource.getMessage(eq("text.factura.flash.lineas.error"), any(), eq(locale)))
                .thenReturn(errorMsg);
        CreateInvoiceDto dto = new CreateInvoiceDto();

        String viewName = adapter.save(null, null, dto, mockBindingResult, model, flash, locale); // item_id[] es null

        assertEquals("invoice/form", viewName);
        verify(model, times(1)).addAttribute("title", title);
        verify(model, times(1)).addAttribute("error", errorMsg);
        verifyNoInteractions(flash);
        verifyNoInteractions(createInvoiceUseCase);
    }

    @Test
    void testSaveItemAmountMismatchError() {
        String title = "New Invoice";
        String errorMsg = "Mismatch items";
        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(messageSource.getMessage(eq("text.factura.form.titulo"), any(), eq(locale)))
                .thenReturn(title);
        when(messageSource.getMessage(eq("text.factura.flash.lineas.error"), any(), eq(locale)))
                .thenReturn(errorMsg);
        CreateInvoiceDto dto = new CreateInvoiceDto();

        UUID[] itemIds = { UUID.randomUUID() };
        Integer[] amounts = { 1, 2 }; // Mismatch

        String viewName = adapter.save(itemIds, amounts, dto, mockBindingResult, model, flash, locale);

        assertEquals("invoice/form", viewName);
        verify(model, times(1)).addAttribute("title", title);
        verify(model, times(1)).addAttribute("error", errorMsg);
        verifyNoInteractions(flash);
        verifyNoInteractions(createInvoiceUseCase);
    }

    @Test
    void testSaveSuccess() {
        String successMsg = "Invoice saved";
        UUID[] itemIds = { UUID.randomUUID(), UUID.randomUUID() };
        Integer[] amounts = { 1, 5 };

        CreateInvoiceDto dto = new CreateInvoiceDto();
        dto.setDescription("Test");
        ClientDto clientDto = new ClientDto();
        clientDto.setUuid(testClientId);
        dto.setClient(clientDto);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(createInvoiceUseCase.create(eq("Test"), any(), eq(testClientId), anyList()))
                .thenReturn(mockInvoice);
        when(mockInvoice.client()).thenReturn(mockClient);
        when(mockClient.uuid()).thenReturn(testClientId);
        when(messageSource.getMessage(eq("text.factura.flash.crear.success"), any(), eq(locale)))
                .thenReturn(successMsg);

        String viewName = adapter.save(itemIds, amounts, dto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/view/" + testClientId, viewName);
        verify(flash, times(1)).addFlashAttribute("success", successMsg);
        verifyNoInteractions(model);

        verify(createInvoiceUseCase).create(any(), any(), any(), itemsCaptor.capture());
        List<Pair<UUID, Integer>> capturedItems = itemsCaptor.getValue();
        assertEquals(2, capturedItems.size());
        assertEquals(itemIds[0], capturedItems.get(0).first());
        assertEquals(amounts[0], capturedItems.get(0).second());
        assertEquals(itemIds[1], capturedItems.get(1).first());
        assertEquals(amounts[1], capturedItems.get(1).second());
    }


    @Test
    void testDeleteInvoiceFound() {
        String successMsg = "Invoice deleted";
        when(findInvoiceUseCase.findById(testId)).thenReturn(mockInvoice);
        when(mockInvoice.client()).thenReturn(mockClient);
        when(mockClient.uuid()).thenReturn(testClientId);
        when(messageSource.getMessage(eq("text.factura.flash.eliminar.success"), any(), eq(locale)))
                .thenReturn(successMsg);

        String viewName = adapter.delete(testId, flash, locale);

        assertEquals("redirect:/view/" + testClientId, viewName);
        verify(deleteInvoiceUseCase, times(1)).delete(testId);
        verify(flash, times(1)).addAttribute("success", successMsg);
    }

    @Test
    void testDeleteInvoiceNotFound() {
        String errorMsg = "Invoice not found";
        when(findInvoiceUseCase.findById(testId)).thenReturn(null);
        when(messageSource.getMessage(eq("text.factura.flash.db.error"), any(), eq(locale)))
                .thenReturn(errorMsg);

        String viewName = adapter.delete(testId, flash, locale);

        assertEquals("redirect:/list", viewName);
        verify(flash, times(1)).addFlashAttribute("error", errorMsg);
        verify(deleteInvoiceUseCase, never()).delete(any());
    }
}