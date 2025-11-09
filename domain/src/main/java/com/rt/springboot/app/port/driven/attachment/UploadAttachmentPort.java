package com.rt.springboot.app.port.driven.attachment;

import com.rt.springboot.app.model.Attachment;

public interface UploadAttachmentPort {
    Attachment upload(String filename, byte[] bytes);
}
