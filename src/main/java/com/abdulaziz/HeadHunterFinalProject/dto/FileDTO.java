package com.abdulaziz.HeadHunterFinalProject.dto;

import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import jakarta.persistence.*;
import lombok.Data;


@Data
public class FileDTO {
    private String fileName;
    private String fullFileName;
}
