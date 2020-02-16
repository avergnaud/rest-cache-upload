package com.poc.restcacheupload.service.mem;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface InMemoryStorageService {

    void init();

    void store(MultipartFile file);

    Map<String, byte[]> loadAll();

    byte[] load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
