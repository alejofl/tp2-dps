import com.rt.springboot.app.adapter.driven.attachment.AttachmentRepository;
import com.rt.springboot.app.adapter.driven.attachment.DeleteAttachmentRelationalAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteAttachmentRelationalAdapterTest {
    @Mock
    private AttachmentRepository attachmentRepository;
    @InjectMocks
    private DeleteAttachmentRelationalAdapter deleteAttachmentRelationalAdapter;

    @Test
    void deleteAttachmentByFilename() {
        String filename = "filename";
        deleteAttachmentRelationalAdapter.delete(filename);
        verify(attachmentRepository).deleteByFilename(filename);
    }
}
