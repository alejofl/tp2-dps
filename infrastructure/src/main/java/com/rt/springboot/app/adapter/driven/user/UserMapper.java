package com.rt.springboot.app.adapter.driven.user;

import com.rt.springboot.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDomain(UserRelationalEntity entity);
}
