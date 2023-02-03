package com.abdulaziz.HeadHunterFinalProject.service;

import com.abdulaziz.HeadHunterFinalProject.dto.SearchDTO;
import com.abdulaziz.HeadHunterFinalProject.dto.VacancyDTO;
import com.abdulaziz.HeadHunterFinalProject.model.VacancyEntity;
import com.abdulaziz.HeadHunterFinalProject.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    private final ModelMapper mapper;

    @Autowired
    public VacancyService(VacancyRepository vacancyRepository, ModelMapper mapper) {
        this.vacancyRepository = vacancyRepository;
        this.mapper = mapper;
    }

    public List<VacancyEntity> findAll(){
        return vacancyRepository.findAll();
    }

    public Optional<VacancyEntity> getById(Long id){
        return Optional.ofNullable(vacancyRepository.findById(id).orElse(null));
    }


    public Page<VacancyDTO> getVacanciesByParams(Pageable pageRequest, SearchDTO dto) {
        Page<VacancyEntity> list = vacancyRepository.findByParams(dto.getLocation(), dto.getTitle(), dto.getUserId(), pageRequest);
        return list.map(v -> mapper.map(v, VacancyDTO.class));
    }


    public List<VacancyEntity> getUnverifiedResume(){
        return vacancyRepository.findByIsVerifyIsFalse();
    }



    @Transactional
    public void save(VacancyEntity vacancyEntity){
        vacancyRepository.save(vacancyEntity);
    }

    @Transactional
    public void update(long id, VacancyEntity vacancyEntity){
        vacancyEntity.setId(id);
        vacancyRepository.save(vacancyEntity);
    }


    @Transactional
    public void delete(long id){
        vacancyRepository.deleteById(id);
    }

}
