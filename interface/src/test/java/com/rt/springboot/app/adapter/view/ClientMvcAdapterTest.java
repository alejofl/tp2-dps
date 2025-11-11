package com.rt.springboot.app.adapter.view;

import com.rt.springboot.app.adapter.driving.ClientMvcAdapter;
import com.rt.springboot.app.adapter.driving.dto.CreateClientDto;
import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.model.Invoice;
import com.rt.springboot.app.port.driving.attachment.FindAttachmentUseCase;
import com.rt.springboot.app.port.driving.attachment.UploadAttachmentUseCase;
import com.rt.springboot.app.port.driving.client.*;
import com.rt.springboot.app.port.driving.invoice.FindInvoicesForClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientMvcAdapterTest {

    @Mock private FindAllClientsUseCase findAllClientsUseCase;
    @Mock private FindClientUseCase findClientUseCase;
    @Mock private CreateClientUseCase createClientUseCase;
    @Mock private UpdateClientUseCase updateClientUseCase;
    @Mock private DeleteClientUseCase deleteClientUseCase;
    @Mock private FindAttachmentUseCase findAttachmentUseCase;
    @Mock private UploadAttachmentUseCase uploadAttachmentUseCase;
    @Mock private FindInvoicesForClientUseCase findInvoicesForClientUseCase;
    @Mock private MessageSource messageSource;
    @Mock private Model model;
    @Mock private Locale locale;
    @Mock private RedirectAttributes flash;
    @Mock private BindingResult mockBindingResult;
    @Mock private MultipartFile mockFile;
    @Mock private Attachment attachment;

    @InjectMocks
    private ClientMvcAdapter adapter;

    private CreateClientDto clientDto;

    @BeforeEach
    void setUp() {
        adapter = new ClientMvcAdapter(
                findAllClientsUseCase,
                findClientUseCase,
                createClientUseCase,
                updateClientUseCase,
                deleteClientUseCase,
                findAttachmentUseCase,
                uploadAttachmentUseCase,
                findInvoicesForClientUseCase,
                messageSource
        );
        clientDto = new CreateClientDto();
        clientDto.setFirstName("Test");
        clientDto.setLastName("User");
    }

    @Test
    void testViewPhoto() {
        String filename = "test-photo.jpg";
        byte[] fileBytes = "dummy-image-data".getBytes();

        when(findAttachmentUseCase.findByFilename(filename)).thenReturn(attachment);

        when(attachment.filename()).thenReturn(filename);
        when(attachment.bytes()).thenReturn(fileBytes);

        ResponseEntity<byte[]> response = adapter.viewPhoto(filename);

        verify(findAttachmentUseCase, times(1)).findByFilename(filename);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileBytes, response.getBody());

        String expectedHeader = String.format("inline; filename=\"%s\"", attachment.filename());
        assertEquals(expectedHeader, response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    void testViewClientFound() {
        UUID testId = UUID.randomUUID();
        String viewTitle = "Detalle de Cliente";

        Client domainClient = new Client(testId, "Lisa", "Simpson", "lisa@mail.com", null, null);
        Invoice invoice1 = new Invoice(UUID.randomUUID(), "Factura Saxofón", null, LocalDate.of(2020, 1, 1), domainClient, null);
        List<Invoice> domainInvoices = List.of(invoice1);

        when(findClientUseCase.findById(testId)).thenReturn(domainClient);
        when(findInvoicesForClientUseCase.findInvoicesForClient(domainClient)).thenReturn(domainInvoices);

        when(messageSource.getMessage(eq("text.cliente.listar.titulo"), any(), eq(locale)))
                .thenReturn(viewTitle);

        String viewName = adapter.view(testId, model, flash, locale);

        assertEquals("view", viewName);

        verify(findClientUseCase, times(1)).findById(testId);
        verify(findInvoicesForClientUseCase, times(1)).findInvoicesForClient(domainClient);

        verify(model, times(1)).addAttribute(eq("client"), any(com.rt.springboot.app.adapter.driving.dto.ClientDto.class));
        verify(model, times(1)).addAttribute(eq("invoices"), any(List.class));
        verify(model, times(1)).addAttribute("title", viewTitle + ": "+ domainClient.firstName());

        verifyNoInteractions(flash);
    }

    @Test
    void testList() {
        Client client1 = new Client(UUID.randomUUID(), "John", "Doe", "john@mail.com", null, null);
        Client client2 = new Client(UUID.randomUUID(), "Jane", "Smith", "jane@mail.com", null, null);
        List<Client> domainClients = List.of(client1, client2);

        String titleMessage = "Client List";

        when(findAllClientsUseCase.findAll()).thenReturn(domainClients);
        when(messageSource.getMessage(eq("text.cliente.listar.titulo"), any(), any(Locale.class)))
                .thenReturn(titleMessage);

        String viewName = adapter.list(model, locale);

        assertEquals("list", viewName);

        verify(findAllClientsUseCase, times(1)).findAll();
        verify(messageSource, times(1)).getMessage(anyString(), any(), any(Locale.class));
        verify(model, times(1)).addAttribute("title", titleMessage);
        verify(model, times(1)).addAttribute(eq("clients"), any(List.class));
    }

    @Test
    void testViewClientNotFound() {
        UUID testId = UUID.randomUUID();
        String errorMessage = "Client not found";

        when(findClientUseCase.findById(testId)).thenReturn(null);
        when(messageSource.getMessage(eq("text.cliente.flash.db.error"), any(), any(Locale.class)))
                .thenReturn(errorMessage);

        String viewName = adapter.view(testId, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(flash, times(1)).addFlashAttribute("error", errorMessage);
        verify(findClientUseCase, times(1)).findById(testId);
        verify(findInvoicesForClientUseCase, never()).findInvoicesForClient(any());
    }

    @Test
    void testSaveWithValidationErrors() {
        String formTitle = "Client Form";

        when(mockBindingResult.hasErrors()).thenReturn(true);

        when(messageSource.getMessage(eq("text.cliente.form.titulo"), any(), eq(locale)))
                .thenReturn(formTitle);

        String viewName = adapter.save(mockFile, clientDto, mockBindingResult, model, flash, locale);

        assertEquals("form", viewName);

        verify(model, times(1)).addAttribute("title", formTitle);

        verifyNoInteractions(flash);
        verifyNoInteractions(uploadAttachmentUseCase);
        verifyNoInteractions(createClientUseCase);
        verifyNoInteractions(updateClientUseCase);
    }

    @Test
    void testSaveNewClient() throws Exception {
        CreateClientDto newClientDto = new CreateClientDto();
        newClientDto.setFirstName("New");
        newClientDto.setLastName("User");
        newClientDto.setEmail("new@mail.com");

        String successMessage = "Client saved successfully";

        org.springframework.web.multipart.MultipartFile mockFile = mock(org.springframework.web.multipart.MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);

        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        when(messageSource.getMessage(eq("text.cliente.flash.crear.success"), any(), any(Locale.class)))
                .thenReturn(successMessage);


        String viewName = adapter.save(mockFile, newClientDto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(flash, times(1)).addFlashAttribute("success", successMessage);

        verify(createClientUseCase, times(1)).create(
                eq("New"),
                eq("User"),
                eq("new@mail.com"),
                any(),
                any()
        );
        verify(updateClientUseCase, never()).update(any(), any(), any(), any(), any(), any());
        verify(uploadAttachmentUseCase, never()).upload(any(), any());
    }

    @Test
    void testSaveNewClientWithAttachment() throws IOException {
        String newFilename = "uploaded-photo.png";
        String originalName = "photo.png";
        byte[] fileBytes = "dummy-data".getBytes();
        String uploadSuccessMsg = "File uploaded: ";
        String createSuccessMsg = "Client created";

        when(mockBindingResult.hasErrors()).thenReturn(false);

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getName()).thenReturn(originalName);
        when(mockFile.getBytes()).thenReturn(fileBytes);

        clientDto.setId(null);

        when(uploadAttachmentUseCase.upload(originalName, fileBytes)).thenReturn(attachment);
        when(attachment.filename()).thenReturn(newFilename);

        when(messageSource.getMessage(eq("text.cliente.flash.foto.subir.success"), any(), eq(locale)))
                .thenReturn(uploadSuccessMsg);
        when(messageSource.getMessage(eq("text.cliente.flash.crear.success"), any(), eq(locale)))
                .thenReturn(createSuccessMsg);

        String viewName = adapter.save(mockFile, clientDto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(uploadAttachmentUseCase, times(1)).upload(originalName, fileBytes);

        verify(flash, times(1)).addFlashAttribute("info", uploadSuccessMsg + "'" + newFilename + "'");
        verify(flash, times(1)).addFlashAttribute("success", createSuccessMsg);

        verify(createClientUseCase, times(1)).create(
                eq(clientDto.getFirstName()),
                eq(clientDto.getLastName()),
                eq(clientDto.getEmail()),
                any(), // createdAt
                eq(newFilename) // El nombre del archivo subido
        );

        verifyNoInteractions(model);
        verifyNoInteractions(updateClientUseCase);
    }

    @Test
    void testSaveUpdateClientNoFile() {
        UUID clientId = UUID.randomUUID();
        String updateSuccessMsg = "Client updated";

        when(mockBindingResult.hasErrors()).thenReturn(false);

        when(mockFile.isEmpty()).thenReturn(true);

        clientDto.setId(clientId);
        clientDto.setPhoto("foto-vieja.jpg");

        when(messageSource.getMessage(eq("text.cliente.flash.editar.success"), any(), eq(locale)))
                .thenReturn(updateSuccessMsg);

        String viewName = adapter.save(mockFile, clientDto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(flash, times(1)).addFlashAttribute("success", updateSuccessMsg);

        verify(flash, never()).addFlashAttribute(eq("info"), anyString());

        verify(updateClientUseCase, times(1)).update(
                eq(clientId),
                eq(clientDto.getFirstName()),
                eq(clientDto.getLastName()),
                eq(clientDto.getEmail()),
                any(), // createdAt
                eq("foto-vieja.jpg") // Se mantiene la foto original
        );

        verifyNoInteractions(uploadAttachmentUseCase);
        verifyNoInteractions(createClientUseCase);
        verifyNoInteractions(model);
    }


    @Test
    void testSaveFileUploadFails() throws IOException {
        String createSuccessMsg = "Client created";
        when(mockBindingResult.hasErrors()).thenReturn(false);

        when(mockFile.isEmpty()).thenReturn(false);

        when(mockFile.getBytes()).thenThrow(new IOException("Error de lectura"));

        clientDto.setId(null);

        when(messageSource.getMessage(eq("text.cliente.flash.crear.success"), any(), eq(locale)))
                .thenReturn(createSuccessMsg);

        String viewName = adapter.save(mockFile, clientDto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(createClientUseCase, times(1)).create(
                eq(clientDto.getFirstName()),
                eq(clientDto.getLastName()),
                eq(clientDto.getEmail()),
                any(),
                eq(null)
        );

        verify(flash, times(1)).addFlashAttribute("success", createSuccessMsg);

        verify(flash, never()).addFlashAttribute(eq("info"), anyString());

        verifyNoInteractions(model);
    }

    @Test
    void testUpdateClientFound() {
        UUID testId = UUID.randomUUID();
        String editTitle = "Edit Client";
        Client domainClient = new Client(testId, "Homer", "Simpson", "homer@mail.com", null, null);

        when(findClientUseCase.findById(testId)).thenReturn(domainClient);

        when(messageSource.getMessage(eq("text.cliente.form.titulo.editar"), any(), eq(locale)))
                .thenReturn(editTitle);

        String viewName = adapter.update(testId, model, flash, locale);

        assertEquals("form", viewName);

        verify(findClientUseCase, times(1)).findById(testId);

        verify(model, times(1)).addAttribute(eq("client"), any(CreateClientDto.class));
        verify(model, times(1)).addAttribute("title", editTitle);

        verifyNoInteractions(flash);
    }

    @Test
    void testUpdateClientNotFound() {
        UUID testId = UUID.randomUUID();
        String errorMessage = "Client not found";

        when(findClientUseCase.findById(testId)).thenReturn(null);

        when(messageSource.getMessage(eq("text.cliente.flash.db.error"), any(), eq(locale)))
                .thenReturn(errorMessage);

        String viewName = adapter.update(testId, model, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(findClientUseCase, times(1)).findById(testId);

        verify(flash, times(1)).addFlashAttribute("error", errorMessage);

        verifyNoInteractions(model);
    }

    @Test
    void testDelete() {
        UUID testId = UUID.randomUUID();
        String deleteSuccessMsg = "Client deleted successfully";

        when(messageSource.getMessage(eq("text.cliente.flash.eliminar.success"), any(), eq(locale)))
                .thenReturn(deleteSuccessMsg);

        String viewName = adapter.delete(testId, flash, locale);

        assertEquals("redirect:/list", viewName);

        verify(deleteClientUseCase, times(1)).delete(testId);

        verify(flash, times(1)).addFlashAttribute("success", deleteSuccessMsg);

        verify(messageSource, times(1)).getMessage(eq("text.cliente.flash.eliminar.success"), any(), eq(locale));
    }
}