package com.rt.springboot.app.model;

public record Attachment(
    String filename,
    byte[] bytes
) {}
