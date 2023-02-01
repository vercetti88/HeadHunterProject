package com.abdulaziz.HeadHunterFinalProject.service;

import com.abdulaziz.HeadHunterFinalProject.dto.ResumeDTO;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import com.abdulaziz.HeadHunterFinalProject.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper mapper;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, FileStorageService fileStorageService, ModelMapper mapper) {
        this.resumeRepository = resumeRepository;
        this.fileStorageService = fileStorageService;
        this.mapper = mapper;
    }

    public List<ResumeEntity> findAll(){
        return resumeRepository.findAll();
    }

    public Optional<ResumeEntity> getById(Long id){
        return Optional.ofNullable(resumeRepository.findById(id).orElse(null));
    }

    @Transactional
    public void save(ResumeDTO resumeDTO){
        ResumeEntity resume = resumeRepository.save(mapper.map(resumeDTO, ResumeEntity.class));
        resumeDTO.setId(resume.getId());
        if(!resumeDTO.getAcceptingFiles().isEmpty()){
        fileStorageService.save(resumeDTO);}
    }

    @Transactional
    public void update(long id, ResumeEntity resumeEntity){
        resumeEntity.setId(id);
        resumeRepository.save(resumeEntity);
    }

    public List<ResumeEntity> getUnverifiedResume(){
        return resumeRepository.findByIsVerifyIsFalse();
    }

    @Transactional
    public void delete(long id){
        resumeRepository.deleteById(id);
    }

}
