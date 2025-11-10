package com.rt.springboot.app.adapter.driving.view.client;

import com.rt.springboot.app.adapter.driving.dto.ClientDto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@XmlRootElement(name = "clients")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class XmlClientList {

    @XmlElement(name = "client")
    private List<ClientDto> clients;

}
