package com.rt.springboot.app.usecase.attachment;

import com.rt.springboot.app.port.driven.attachment.DeleteAttachmentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAttachmentUseCaseImplTest {

    @Mock
    private DeleteAttachmentPort deleteAttachmentPort;

    @InjectMocks
    private DeleteAttachmentUseCaseImpl deleteAttachmentUseCase;

    private String filename;

    @BeforeEach
    void setUp() {
        this.filename = "photo.jpg";
    }

    @Test
    @DisplayName("Should delete attachment successfully")
    void shouldDeleteAttachmentSuccessfully() {
        // Given
        doNothing().when(deleteAttachmentPort).delete(filename);

        // When & Then
        assertDoesNotThrow(() -> deleteAttachmentUseCase.delete(filename));

        verify(deleteAttachmentPort, times(1)).delete(filename);
        verifyNoMoreInteractions(deleteAttachmentPort);
    }

}

