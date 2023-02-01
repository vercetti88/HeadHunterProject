package com.abdulaziz.HeadHunterFinalProject.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class VacancyDTO {
    private String  jobTitle;
    private String aboutCompany;
    private String location;
    private String requirements;
    private int salary;
    private String workType;
    private String experience;
    private String aboutVacancy;
    private boolean isVerify;
}
