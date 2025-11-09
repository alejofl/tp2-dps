package com.rt.springboot.app.port.driving.attachment;

import com.rt.springboot.app.model.Attachment;

public interface FindAttachmentUseCase {
    Attachment findByFilename(String filename);
}
