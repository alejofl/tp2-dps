package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachmentMapper {
    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    Attachment toDomain(AttachmentRelationalEntity entity);
}
