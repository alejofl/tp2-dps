package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.adapter.driving.dto.ClientMapper;
import com.rt.springboot.app.adapter.driving.dto.CreateClientDto;
import com.rt.springboot.app.adapter.driving.dto.InvoiceMapper;
import com.rt.springboot.app.annotation.DrivingAdapter;
import com.rt.springboot.app.port.driving.attachment.FindAttachmentUseCase;
import com.rt.springboot.app.port.driving.attachment.UploadAttachmentUseCase;
import com.rt.springboot.app.port.driving.client.*;
import com.rt.springboot.app.port.driving.invoice.FindInvoicesForClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@DrivingAdapter
@RequiredArgsConstructor
@SessionAttributes("client")
public class ClientMvcAdapter {

    private final FindAllClientsUseCase findAllClientsUseCase;
    private final FindClientUseCase findClientUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final FindAttachmentUseCase findAttachmentUseCase;
    private final UploadAttachmentUseCase uploadAttachmentUseCase;
    private final FindInvoicesForClientUseCase findInvoicesForClientUseCase;

    private final MessageSource messageSource;

    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> viewPhoto(
            @PathVariable String filename
    ) {
        final var resource = findAttachmentUseCase.findByFilename(filename);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"%s\"", resource.filename()))
                .body(resource.bytes());
    }

    @Secured("ROLE_USER")
    @GetMapping("/view/{id}")
    public String view(
            @PathVariable(value = "id") UUID id,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        final var client = findClientUseCase.findById(id);
        if (client == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/list";
        }
        final var invoices = findInvoicesForClientUseCase.findInvoicesForClient(client)
                .stream()
                .map(InvoiceMapper.INSTANCE::toDto)
                .toList();

        model.addAttribute("client", ClientMapper.INSTANCE.toDto(client));
        model.addAttribute("invoices", invoices);
        model.addAttribute("title", messageSource.getMessage("text.cliente.listar.titulo", null, locale) + ": "+ client.firstName());

        return "view";
    }

    @Secured("ROLE_USER")
    @GetMapping({ "/list", "/" })
    public String list(
            Model model,
            Locale locale
    ) {
        final var clients = findAllClientsUseCase
                .findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toDto)
                .toList();

        model.addAttribute("title", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
        model.addAttribute("clients", clients);

        return "list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String create(
            Model model,
            Locale locale
    ) {
        final var client = new CreateClientDto();

        model.addAttribute("client", client);
        model.addAttribute("title", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));

        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String update(
            @PathVariable(value = "id") UUID id,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        final var client = findClientUseCase.findById(id);

        if (client == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/list";
        }
        model.addAttribute("client", ClientMapper.INSTANCE.toCreateDto(client));
        model.addAttribute("title", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));

        return "form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String save(
            @RequestParam("file") MultipartFile photo,
            @Valid CreateClientDto client,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        if (result.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("text.cliente.form.titulo", null, locale));
            return "form";
        }

        if (!photo.isEmpty()) {
            try {
                final var resource = uploadAttachmentUseCase.upload(
                        photo.getName(),
                        photo.getBytes()
                );

                flash.addFlashAttribute(
                        "info",
                        messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + resource.filename() + "'"
                );
                client.setPhoto(resource.filename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final var flashMsg = (client.getId() != null)
                ? messageSource.getMessage("text.cliente.flash.editar.success", null, locale)
                : messageSource.getMessage("text.cliente.flash.crear.success", null, locale);

        if (client.getId() != null) {
            updateClientUseCase.update(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getCreatedAt(), client.getPhoto());
        } else {
            createClientUseCase.create(client.getFirstName(), client.getLastName(), client.getEmail(), client.getCreatedAt(), client.getPhoto());
        }

        flash.addFlashAttribute("success", flashMsg);
        return "redirect:/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable(value = "id") UUID id,
            RedirectAttributes flash,
            Locale locale
    ) {
        deleteClientUseCase.delete(id);

        flash.addFlashAttribute("success", messageSource.getMessage("text.cliente.flash.eliminar.success", null, locale));

        return "redirect:/list";
    }
}
