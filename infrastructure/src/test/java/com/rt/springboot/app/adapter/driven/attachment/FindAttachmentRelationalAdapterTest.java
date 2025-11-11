package com.rt.springboot.app.adapter.driven.attachment;

import com.rt.springboot.app.model.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAttachmentRelationalAdapterTest {

    @Mock
    private AttachmentRepository repository;

    @InjectMocks
    private FindAttachmentRelationalAdapter adapter;

    @Test
    void findByFilename() {
        AttachmentRelationalEntity entity = new AttachmentRelationalEntity();
        entity.setId(1L);
        entity.setFilename("filename");
        entity.setBytes("123".getBytes());

        when(repository.findByFilename("filename")).thenReturn(entity);

        Attachment result = adapter.findByFilename("filename");

        verify(repository).findByFilename("filename");

        assertThat(result).isNotNull();
        assertThat(result.filename()).isEqualTo("filename");
        assertThat(result.bytes()).containsExactly("123".getBytes());
    }
}
