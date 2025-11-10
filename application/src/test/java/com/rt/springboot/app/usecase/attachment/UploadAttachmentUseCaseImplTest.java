package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.UploadAttachmentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UploadAttachmentUseCaseImplTest {

    @Mock
    private UploadAttachmentPort uploadAttachmentPort;

    @InjectMocks
    private UploadAttachmentUseCaseImpl uploadAttachmentUseCase;

    private String filename;
    private byte[] bytes;

    @BeforeEach
    void setUp() {
        this.filename = "photo.jpg";
        this.bytes = "test content".getBytes();
    }

    @Test
    @DisplayName("Should upload attachment successfully with valid data")
    void shouldUploadAttachmentSuccessfully() {
        // Given
        String expectedFilename = "uuid-photo.jpg";
        Attachment expectedAttachment = new Attachment(expectedFilename, bytes);
        
        when(uploadAttachmentPort.upload(anyString(), eq(bytes))).thenReturn(expectedAttachment);

        // When
        Attachment result = uploadAttachmentUseCase.upload(filename, bytes);

        // Then
        assertNotNull(result);
        assertEquals(expectedAttachment.filename(), result.filename());
        assertArrayEquals(bytes, result.bytes());
    }

}

