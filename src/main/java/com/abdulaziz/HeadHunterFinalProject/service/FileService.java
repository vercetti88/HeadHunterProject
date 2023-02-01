package com.abdulaziz.HeadHunterFinalProject.service;

import com.abdulaziz.HeadHunterFinalProject.dto.FileDTO;
import com.abdulaziz.HeadHunterFinalProject.model.FileEntity;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import com.abdulaziz.HeadHunterFinalProject.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final ModelMapper mapper;

    @Autowired
    public FileService(FileRepository fileRepository, ModelMapper mapper) {
        this.fileRepository = fileRepository;
        this.mapper = mapper;
    }

    public List<FileEntity> getAllFiles(long id){
        return fileRepository.findAllById(Collections.singleton(id));
    }


    @Transactional
    public void save(FileEntity file){
        fileRepository.save(file);
    }

    public FileEntity getById(Long id){
        return fileRepository.getById(id);
    }

    public List<FileDTO> getByResume(ResumeEntity entity){
        return fileRepository.findAllByResumeEntity(entity).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public FileDTO convertToDto(FileEntity fileEntity){
        return mapper.map(fileEntity,FileDTO.class);
    }


    @Transactional
    public void deleteById(Long id){
        fileRepository.deleteById(id);
    }



}
