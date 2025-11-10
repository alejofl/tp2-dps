package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(uses = {CommonFieldsMapper.class})
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mappings({
            @Mapping(source = "uuid", target = "id", qualifiedByName = "uuidToString"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
    })
    ClientDto toDto(Client client);

    @Mapping(source = "uuid", target = "id")
    CreateClientDto toCreateDto(Client client);
}