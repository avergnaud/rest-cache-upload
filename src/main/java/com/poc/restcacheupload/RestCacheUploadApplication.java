package com.poc.restcacheupload;

import com.poc.restcacheupload.service.fs.FileSystemStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RestCacheUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestCacheUploadApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileSystemStorageService fileSystemStorageService) {
		return (args) -> {
			fileSystemStorageService.deleteAll();
			fileSystemStorageService.init();
		};
	}

}
