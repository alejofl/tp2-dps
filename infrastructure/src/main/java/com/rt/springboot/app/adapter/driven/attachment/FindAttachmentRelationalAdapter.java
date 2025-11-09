package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.FindAttachmentPort;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class FindAttachmentRelationalAdapter implements FindAttachmentPort {
    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment findByFilename(String filename) {
        final var entity = this.attachmentRepository.findByFilename(filename);
        return AttachmentMapper.INSTANCE.toDomain(entity);
    }
}
