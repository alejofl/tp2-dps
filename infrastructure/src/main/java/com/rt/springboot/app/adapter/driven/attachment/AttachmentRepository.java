package com.rt.springboot.app.adapter.driven.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<AttachmentRelationalEntity, Long> {
    void deleteByFilename(String filename);

    AttachmentRelationalEntity findByFilename(String filename);
}
