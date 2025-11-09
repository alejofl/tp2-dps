package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.port.driven.attachment.DeleteAttachmentPort;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class DeleteAttachmentRelationalAdapter implements DeleteAttachmentPort {
    private final AttachmentRepository attachmentRepository;

    @Override
    public void delete(String filename) {
        this.attachmentRepository.deleteByFilename(filename);
    }
}
