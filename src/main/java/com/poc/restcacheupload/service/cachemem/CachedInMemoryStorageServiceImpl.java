package com.poc.restcacheupload.service.cachemem;

import com.poc.restcacheupload.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Qualifier("withcache")
public class CachedInMemoryStorageServiceImpl implements CachedInMemoryStorageService {

    @Autowired
    AttachedFilesService attachedFilesService;

    @Override
    public void init() {
        //
    }

    @Override
    public void store(String sessionId, MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if (multipartFile.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }

        try {
            attachedFilesService.attachedFiles(sessionId).put(filename, multipartFile.getBytes());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

    }

    @Override
    public Map<String, byte[]> loadAll(String sessionId) {
        return attachedFilesService.attachedFiles(sessionId);
    }

    @Override
    public byte[] load(String sessionId, String filename) {
        return attachedFilesService.attachedFiles(sessionId).get(filename);
    }

    @Override
    public Resource loadAsResource(String sessionId, String filename) {
        Resource resource = new ByteArrayResource(attachedFilesService.attachedFiles(sessionId).get(filename));
        return resource;
    }

    @Override
    public void deleteAll() {
        //
    }
}
