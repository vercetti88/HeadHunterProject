package com.abdulaziz.HeadHunterFinalProject.service;


import com.abdulaziz.HeadHunterFinalProject.dto.ResumeDTO;
import com.abdulaziz.HeadHunterFinalProject.model.FileEntity;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FileStorageServicee implements FileStorageService {

    private final ModelMapper modelMapper;
//    private final ResumeService resumeService;


    private final FileService fileService;
    private final Path root = Paths.get("C:\\Users\\Abdulaziz\\Desktop\\ResumeFiles");

    @Override
    @Transactional
    public void save(ResumeDTO resumeDTO) {
        try {
            List<MultipartFile> files = resumeDTO.getAcceptingFiles();
            ResumeEntity resume = modelMapper.map(resumeDTO, ResumeEntity.class);

            for(MultipartFile file : files) {
                Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName("File"+resumeDTO.getName());
                fileEntity.setFullFileName(file.getOriginalFilename());
                fileEntity.setResumeEntity(resume);
                fileService.save(fileEntity);
            }

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Resource getFile(Long id) {
        try {
            FileEntity fileEntity = fileService.getById(id);
            String filename = fileEntity.getFullFileName();
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            FileEntity fileEntity = fileService.getById(id);
            String filename = fileEntity.getFullFileName();
            Path file = root.resolve(filename); //TODO вытащить файл по id, метод принимает имя файла
            fileService.deleteById(id);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
