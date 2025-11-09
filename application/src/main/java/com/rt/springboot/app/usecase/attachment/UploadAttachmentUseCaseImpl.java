package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.UploadAttachmentPort;
import com.rt.springboot.app.port.driving.attachment.UploadAttachmentUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadAttachmentUseCaseImpl implements UploadAttachmentUseCase {
    private final UploadAttachmentPort uploadAttachmentPort;

    @Override
    public Attachment upload(String filename, byte[] bytes) {
        return this.uploadAttachmentPort.upload(
                String.format("%s_%s", UUID.randomUUID(), filename),
                bytes
        );
    }
}
