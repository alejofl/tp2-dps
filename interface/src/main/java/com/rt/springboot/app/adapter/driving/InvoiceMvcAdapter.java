package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.Pair;
import com.rt.springboot.app.adapter.driving.dto.ClientMapper;
import com.rt.springboot.app.adapter.driving.dto.CreateInvoiceDto;
import com.rt.springboot.app.adapter.driving.dto.InvoiceDto;
import com.rt.springboot.app.adapter.driving.dto.InvoiceMapper;
import com.rt.springboot.app.annotation.DrivingAdapter;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import com.rt.springboot.app.port.driving.invoice.CreateInvoiceUseCase;
import com.rt.springboot.app.port.driving.invoice.DeleteInvoiceUseCase;
import com.rt.springboot.app.port.driving.invoice.FindInvoiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.IntStream;

@Secured("ROLE_ADMIN")
@DrivingAdapter
@RequiredArgsConstructor
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceMvcAdapter {

    private final DeleteInvoiceUseCase deleteInvoiceUseCase;
    private final FindInvoiceUseCase findInvoiceUseCase;
    private final FindClientUseCase findClientUseCase;
    private final CreateInvoiceUseCase createInvoiceUseCase;

    private final MessageSource messageSource;

    @GetMapping("/view/{id}")
    public String view(
            @PathVariable(value = "id") UUID id,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        final var invoice = findInvoiceUseCase.findById(id);
        if (invoice == null) {
            flash.addAttribute("error", messageSource.getMessage("text.factura.flash.db.error", null, locale));
            return "redirect:/list";
        }

        model.addAttribute("invoice", InvoiceMapper.INSTANCE.toDto(invoice));
        model.addAttribute("title", String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), invoice.getDescription()));

        return "invoice/view";
    }

    @GetMapping("/form/{clientId}")
    public String create(
            @PathVariable(value = "clientId") UUID clientId,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        final var client = findClientUseCase.findById(clientId);

        if (client == null) {
            flash.addAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/list";
        }

        final var clientDto = ClientMapper.INSTANCE.toDto(client);
        final var invoice = new CreateInvoiceDto();
        invoice.setClient(clientDto);

        model.addAttribute("invoice", invoice);
        model.addAttribute("title", messageSource.getMessage("text.factura.form.titulo", null, locale));

        return "invoice/form";
    }

    @PostMapping("/form")
    public String save(
            @RequestParam(name = "item_id[]", required = false) UUID[] itemId,
            @RequestParam(name = "amount[]", required = false) Integer[] amount,
            @Valid CreateInvoiceDto createInvoiceDto,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        if (result.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("text.factura.form.titulo", null, locale));
            return "invoice/form";
        }

        if (itemId == null || itemId.length == 0 || itemId.length != amount.length) {
            model.addAttribute("title", messageSource.getMessage("text.factura.form.titulo", null, locale));
            model.addAttribute("error", messageSource.getMessage("text.factura.flash.lineas.error", null, locale));
            return "invoice/form";
        }

        final var items = IntStream
                .range(0, itemId.length)
                .mapToObj(i -> new Pair<>(itemId[i], amount[i]))
                .toList();
        final var invoice = createInvoiceUseCase.create(
                createInvoiceDto.getDescription(),
                createInvoiceDto.getObservation(),
                createInvoiceDto.getClient().getUuid(),
                items
        );

        flash.addFlashAttribute("success", messageSource.getMessage("text.factura.flash.crear.success", null, locale));
        return "redirect:/view/" + invoice.getClient().getUuid();
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable(value = "id") UUID id,
            RedirectAttributes flash,
            Locale locale
    ) {
        final var invoice = findInvoiceUseCase.findById(id);

        if (invoice != null) {
            deleteInvoiceUseCase.delete(id);
            flash.addAttribute("success", messageSource.getMessage("text.factura.flash.eliminar.success", null, locale));
            return "redirect:/view/" + invoice.getClient().getUuid();
        }

        flash.addFlashAttribute("error", messageSource.getMessage("text.factura.flash.db.error", null, locale));
        return "redirect:/list";
    }
}
