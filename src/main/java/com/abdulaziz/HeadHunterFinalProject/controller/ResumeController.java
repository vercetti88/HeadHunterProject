package com.abdulaziz.HeadHunterFinalProject.controller;


import com.abdulaziz.HeadHunterFinalProject.dto.ResumeDTO;
import com.abdulaziz.HeadHunterFinalProject.dto.SearchDTO;
import com.abdulaziz.HeadHunterFinalProject.message.ResponseMessage;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import com.abdulaziz.HeadHunterFinalProject.model.VacancyEntity;
import com.abdulaziz.HeadHunterFinalProject.service.FileService;
import com.abdulaziz.HeadHunterFinalProject.service.FileStorageService;
import com.abdulaziz.HeadHunterFinalProject.service.ResumeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ModelMapper mapper;

    private final FileService fileService;

    private final FileStorageService storageService;


    @Autowired
    public ResumeController(ResumeService resumeService, ModelMapper mapper, FileService fileService, FileStorageService storageService) {
        this.resumeService = resumeService;
        this.mapper = mapper;
        this.fileService = fileService;
        this.storageService = storageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>>  getResumeWithFiles(@PathVariable("id") long id){
        Map<String, Object> map = new HashMap<>();
        Optional<ResumeEntity> resume = resumeService.getById(id);
        map.put("resume", convertToResumeDTO(resume.get()));
        map.put("files", fileService.getByResume(resume.get()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public Optional<ResumeDTO> getResumeById(@PathVariable("id") long id){
//        return Optional.ofNullable(convertToResumeDTO(resumeService.getById(id).get()));
//    }


    @GetMapping("/verification")
    public List<ResumeEntity> getUnverifiedVacancy(){
        return resumeService.getUnverifiedResume();
    }


    @PostMapping("/verification/{id}")
    public ResponseEntity<HttpStatus> verifyResume(@PathVariable("id") long id){
        resumeService.getById(id).get().setIsVerify(true);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ResumeEntity>> search(@RequestBody SearchDTO searchDTO, Pageable pageable){
        return new ResponseEntity<>(resumeService.getResumesByParams(pageable,searchDTO), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody ResumeDTO resumeDTO){
        resumeService.save(resumeDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping(value = "/files",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<ResponseMessage> uploadFiles(ResumeDTO resumeDTO) {

        String message = "";
        try {
            resumeService.save(resumeDTO);
            message = "Uploaded the file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody ResumeDTO resumeDTO, @PathVariable long id){
        resumeService.update(id,convertToResume(resumeDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") long id){
        resumeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResumeEntity convertToResume(ResumeDTO resumeDTO){
        return mapper.map(resumeDTO, ResumeEntity.class);
    }
    public ResumeDTO convertToResumeDTO(ResumeEntity resumeEntity){
        return mapper.map(resumeEntity, ResumeDTO.class);
    }

}
