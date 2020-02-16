package com.poc.restcacheupload.service.mem;

import com.poc.restcacheupload.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("nocache")
public class InMemoryStorageServiceImpl implements InMemoryStorageService {

    Map<String, byte[]> filesMap = new HashMap<>();;

    @Override
    public void init() {
        //
    }

    @Override
    public void store(MultipartFile multipartFile) {
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
            filesMap.put(filename, multipartFile.getBytes());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

    }

    @Override
    public Map<String, byte[]> loadAll() {
        return filesMap;
    }

    @Override
    public byte[] load(String filename) {
        return filesMap.get(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Resource resource = new ByteArrayResource(filesMap.get(filename));
        return resource;
    }

    @Override
    public void deleteAll() {
        filesMap = new HashMap<>();
    }
}
