package com.abdulaziz.HeadHunterFinalProject.controller;


import com.abdulaziz.HeadHunterFinalProject.dto.SearchDTO;
import com.abdulaziz.HeadHunterFinalProject.dto.VacancyDTO;
import com.abdulaziz.HeadHunterFinalProject.model.VacancyEntity;
import com.abdulaziz.HeadHunterFinalProject.service.VacancyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacancy")
public class VacancyController {
    private final ModelMapper mapper;

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(ModelMapper mapper, VacancyService vacancyService) {
        this.mapper = mapper;
        this.vacancyService = vacancyService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<VacancyDTO> getVacancies(){
        return vacancyService.findAll().stream().map(this::convertToVacancyDTO).collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/{id}")
    public Optional<VacancyDTO> getVacancyById(@PathVariable("id") long id){
        return Optional.ofNullable(convertToVacancyDTO(vacancyService.getById(id).get()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/search")
    public Page<VacancyDTO> findByParams(Pageable pageRequest, @RequestBody SearchDTO dto){
        return vacancyService.getVacanciesByParams(pageRequest,dto);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody VacancyDTO vacancyDTO){
        vacancyService.save(convertToVacancy(vacancyDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody VacancyDTO vacancyDTO, @PathVariable long id){
        vacancyService.update(id,convertToVacancy(vacancyDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/verification/{id}")
    public ResponseEntity<HttpStatus> verifyResume(@PathVariable("id") long id){
        vacancyService.getById(id).get().setVerify(true);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/verification")
    public List<VacancyEntity> getUnverifiedVacancy(){
        return vacancyService.getUnverifiedResume();
    }

    public VacancyEntity convertToVacancy(VacancyDTO vacancyDTO){
        return mapper.map(vacancyDTO, VacancyEntity.class);
    }
    public VacancyDTO convertToVacancyDTO(VacancyEntity vacancyEntity){
        return mapper.map(vacancyEntity, VacancyDTO.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id){
        vacancyService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }




}
