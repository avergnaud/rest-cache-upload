package com.poc.restcacheupload.controller;

import com.poc.restcacheupload.exceptions.StorageFileNotFoundException;
import com.poc.restcacheupload.service.cachemem.CachedInMemoryStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cachemem")
public class CachedInMemoryFileUploadController {

    @Autowired
    private CachedInMemoryStorageService cachedInMemoryStorageService;

    @RequestMapping(method = RequestMethod.GET)
    public String listUploadedFiles(HttpSession session, Model model) throws IOException {

        model.addAttribute("files", cachedInMemoryStorageService.loadAll(session.getId())
                .entrySet()
                .stream()
                .map(entry -> MvcUriComponentsBuilder.fromMethodName(CachedInMemoryFileUploadController.class,
                            "serveFile", session, entry.getKey()).build().toString())
                .collect(Collectors.toList()));

        return "cachemem/uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(HttpSession session, @PathVariable String filename) {

        Resource file = cachedInMemoryStorageService.loadAsResource(session.getId(), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(HttpSession session,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        cachedInMemoryStorageService.store(session.getId(), file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/cachemem";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
