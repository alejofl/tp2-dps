package com.rt.springboot.app.port.driving.file;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

public interface LoadFileUseCase {
    Resource load(String filename) throws MalformedURLException;
}

