package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.model.Attachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
public class UploadAttachmentRelationalAdapterTest {

    @Mock
    private AttachmentRepository attachmentRepository;
    @InjectMocks
    private UploadAttachmentRelationalAdapter uploadAttachmentRelationalAdapter;

    private String filename = "filename";
    private byte[] bytes;

    @BeforeEach
    void setUp() {
        filename = "filename";
        bytes = new byte[]{1, 2, 3};
    }

    @Test
    void upload() {
        Attachment result = uploadAttachmentRelationalAdapter.upload(filename, bytes);

        assertThat(result).isNotNull();
        assertThat(result.filename()).isEqualTo(filename);
        assertThat(result.bytes()).isEqualTo(bytes);
    }
}
