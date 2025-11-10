package com.rt.springboot.app.adapter.driving.view.client;

import com.rt.springboot.app.adapter.driving.dto.ClientDto;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("list.csv")
public class ClientListCsvView extends AbstractView {

    public ClientListCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
        response.setContentType(getContentType());

        final var clients = (List<ClientDto>) model.get("clients");

        try (final var beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)){
            final String[] header = {"id", "firstName", "lastName", "email", "createdAt"};
            beanWriter.writeHeader(header);

            clients.forEach(client -> {
                try{
                    beanWriter.write(client,header);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}