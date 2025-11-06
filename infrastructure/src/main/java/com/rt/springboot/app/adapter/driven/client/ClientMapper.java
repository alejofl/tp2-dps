package com.rt.springboot.app.adapter.driven.client;

import com.rt.springboot.app.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toDomain(ClientRelationalEntity clientRelationalEntity);
}
