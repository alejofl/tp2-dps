package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.UploadAttachmentPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DrivenAdapter
@RequiredArgsConstructor
public class UploadAttachmentRelationalAdapter implements UploadAttachmentPort {
    private final AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public Attachment upload(String filename, byte[] bytes) {
        final var entity = new AttachmentRelationalEntity();
        entity.setFilename(filename);
        entity.setBytes(bytes);
        this.attachmentRepository.save(entity);
        return AttachmentMapper.INSTANCE.toDomain(entity);
    }
}
