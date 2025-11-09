package com.rt.springboot.app.model;

import lombok.Data;

@Data
public class Attachment {
    private String filename;
    private byte[] bytes;
}
