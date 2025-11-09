package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.adapter.driving.dto.ClientDto;
import com.rt.springboot.app.adapter.driving.dto.ClientMapper;
import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driving.client.DeleteClientUseCase;
import com.rt.springboot.app.port.driving.client.FindAllClientsUseCase;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Adapter
@RequiredArgsConstructor
@SessionAttributes("client")
public class ClientMvcAdapter {

    private final FindAllClientsUseCase findAllClientsUseCase;
    private final FindClientUseCase findClientUseCase;
    private final CreateOrUpdateClientUseCase createOrUpdateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;

    private final MessageSource messageSource;

    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(
            @PathVariable String filename
    ) {
        // TODO
        final var resource = uploadFileService.load(filename);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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

        model.addAttribute("client", client);
        model.addAttribute("title", messageSource.getMessage("text.cliente.listar.titulo", null, locale) + ": "+ client.getFirstName());

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
        // TODO map to createclientdto
        final var client = findClientUseCase.findById(id);

        if (client == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/list";
        }
        model.addAttribute("client", client);
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
            // TODO upload photo

            flash.addFlashAttribute("info", messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + uniqueFilename + "'");
            client.setPhoto(filename);
        }

        final var flashMsg = (client.getId() != null)
                ? messageSource.getMessage("text.cliente.flash.editar.success", null, locale)
                : messageSource.getMessage("text.cliente.flash.crear.success", null, locale);

        createOrUpdateClientUseCase.createOrUpdate(client);

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
