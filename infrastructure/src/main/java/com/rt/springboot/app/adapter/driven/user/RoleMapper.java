package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toDomain(RoleRelationalEntity entity);
}
