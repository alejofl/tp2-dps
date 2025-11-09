package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.port.driven.attachment.DeleteAttachmentPort;
import com.rt.springboot.app.port.driving.attachment.DeleteAttachmentUseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteAttachmentUseCaseImpl implements DeleteAttachmentUseCase {
    private final DeleteAttachmentPort deleteAttachmentPort;

    @Override
    public void delete(String filename) {
        this.deleteAttachmentPort.delete(filename);
    }
}
