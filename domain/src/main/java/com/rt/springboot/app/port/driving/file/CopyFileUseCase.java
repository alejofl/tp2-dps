package com.rt.springboot.app.port.driving.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CopyFileUseCase {
    String copy(MultipartFile file) throws IOException;
}

