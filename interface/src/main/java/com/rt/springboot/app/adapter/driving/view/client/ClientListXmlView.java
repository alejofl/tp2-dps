package com.rt.springboot.app.adapter.driving.view.client;

import java.util.List;
import java.util.Map;

import com.rt.springboot.app.adapter.driving.dto.ClientDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

@Component("list.xml")
public class ClientListXmlView extends MarshallingView {

	public ClientListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {

		model.remove("title");
		model.remove("page");

		final var clients = (List<ClientDto>) model.get("clients");
		
		model.remove("clients");
		
		model.put("clientList", new XmlClientList(clients));
		
		super.renderMergedOutputModel(model, request, response);
	}
	
}
