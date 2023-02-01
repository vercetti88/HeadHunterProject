package com.abdulaziz.HeadHunterFinalProject.controller;


import com.abdulaziz.HeadHunterFinalProject.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") long id){
        Resource resource = fileStorageService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable("id") long id){
        fileStorageService.delete(id);
    }

}
