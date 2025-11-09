package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.annotation.UseCase;
import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.FindAttachmentPort;
import com.rt.springboot.app.port.driving.attachment.FindAttachmentUseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindAttachmentUseCaseImpl implements FindAttachmentUseCase {
    private final FindAttachmentPort findAttachmentPort;

    @Override
    public Attachment findByFilename(String filename) {
        return this.findAttachmentPort.findByFilename(filename);
    }
}
