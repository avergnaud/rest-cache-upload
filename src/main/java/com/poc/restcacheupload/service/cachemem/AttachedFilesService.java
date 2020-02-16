package com.poc.restcacheupload.service.cachemem;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttachedFilesService {

    @Cacheable(
            value = "attachedFilesCache",
            key = "#sessionId"
    )
    public Map<String, byte[]> attachedFiles(String sessionId) {
        Map<String, byte[]> attachedFilesUploadMap = new HashMap<>();
        return attachedFilesUploadMap;
    }
}
