package com.poc.restcacheupload.service.cachemem;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CachedInMemoryStorageService {

    void init();

    void store(String sessionId, MultipartFile file);

    Map<String, byte[]> loadAll(String sessionId);

    byte[] load(String sessionId, String filename);

    Resource loadAsResource(String sessionId, String filename);

    void deleteAll();
}
