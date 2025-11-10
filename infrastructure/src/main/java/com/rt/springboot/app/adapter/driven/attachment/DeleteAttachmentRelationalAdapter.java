package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.annotation.DrivenAdapter;
import com.rt.springboot.app.port.driven.attachment.DeleteAttachmentPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DrivenAdapter
@RequiredArgsConstructor
public class DeleteAttachmentRelationalAdapter implements DeleteAttachmentPort {
    private final AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public void delete(String filename) {
        this.attachmentRepository.deleteByFilename(filename);
    }
}
