package com.rt.springboot.app.port.driven.attachment;

import com.rt.springboot.app.model.Attachment;

public interface FindAttachmentPort {
    Attachment findByFilename(String filename);
}
