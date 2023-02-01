package com.abdulaziz.HeadHunterFinalProject.repository;

import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    public List<ResumeEntity> findByIsVerifyIsFalse();
}
