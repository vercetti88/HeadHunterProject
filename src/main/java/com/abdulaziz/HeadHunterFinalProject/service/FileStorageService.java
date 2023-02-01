package com.abdulaziz.HeadHunterFinalProject.service;

import com.abdulaziz.HeadHunterFinalProject.dto.ResumeDTO;
import org.springframework.core.io.Resource;

public interface FileStorageService {
    void save(ResumeDTO resumeDTO);

    Resource getFile(Long id);

    boolean delete(Long id);
}
