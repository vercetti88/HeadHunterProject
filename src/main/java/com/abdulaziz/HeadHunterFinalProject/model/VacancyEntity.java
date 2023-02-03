package com.abdulaziz.HeadHunterFinalProject.model;


import com.abdulaziz.HeadHunterFinalProject.repository.VacancyRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Entity
@Table(name = "vacancy",schema = "opa")
@Data
@NoArgsConstructor
public class VacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "is_verify")
    private boolean isVerify = false;

    @Column(name = "job_title")
    private String  jobTitle;
    @Column(name = "about_company")
    private String aboutCompany;
    @Column(name = "location")
    private String location;
    @Column(name = "requirements")
    private String requirements;
    @Column(name = "salary")
    private int salary;
    @Column(name = "work_type")
    private String workType;
    @Column(name = "experience")
    private String experience;
    @Column(name = "about_vacancy")
    private String aboutVacancy;
    @Column(name = "create_date")
    private Date createDate = new Date();


}
