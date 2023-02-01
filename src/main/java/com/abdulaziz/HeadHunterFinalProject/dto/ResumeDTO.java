package com.abdulaziz.HeadHunterFinalProject.dto;


import com.abdulaziz.HeadHunterFinalProject.model.FileEntity;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResumeDTO {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private String email;
    private String phone;
    private String gender;
    private Boolean isMarried;
    private String education;
    private String location;
    private String birthday;
    private String position;
    private int salary ;
    private String skills;
    private String workPlace;
    private String aboutMe;
    private String languages;
    private boolean isVerify;
    private List<MultipartFile> acceptingFiles;

}
