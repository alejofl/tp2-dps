import com.rt.springboot.app.adapter.driven.attachment.*;
import com.rt.springboot.app.model.Attachment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FindAttachmentRelationalAdapterTest {

    @Test
    void findByFilename() {
        AttachmentRepository repository = mock(AttachmentRepository.class);

        FindAttachmentRelationalAdapter adapter = new FindAttachmentRelationalAdapter(repository);

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
