package com.abdulaziz.HeadHunterFinalProject.repository;

import com.abdulaziz.HeadHunterFinalProject.model.FileEntity;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    public List<FileEntity> findAllByResumeEntity(ResumeEntity resume);
}
