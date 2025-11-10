package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.model.Attachment;
import com.rt.springboot.app.port.driven.attachment.FindAttachmentPort;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAttachmentUseCaseImplTest {

    @Mock
    private FindAttachmentPort findAttachmentPort;

    @InjectMocks
    private FindAttachmentUseCaseImpl findAttachmentUseCase;

    private String filename;
    private byte[] bytes;

    @BeforeEach
    void setUp() {
        this.filename = "photo.jpg";
        this.bytes = "test content".getBytes();
    }

    @Test
    @DisplayName("Should find attachment by filename when attachment exists")
    void shouldFindAttachmentByFilenameWhenAttachmentExists() {
        // Given
        Attachment expectedAttachment = new Attachment(filename, bytes);
        when(findAttachmentPort.findByFilename(filename)).thenReturn(expectedAttachment);

        // When
        Attachment result = findAttachmentUseCase.findByFilename(filename);

        // Then
        assertNotNull(result);
        assertEquals(expectedAttachment.filename(), result.filename());
        assertArrayEquals(bytes, result.bytes());
    }

}

