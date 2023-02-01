package com.abdulaziz.HeadHunterFinalProject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "resume",schema = "opa")
@Data
@NoArgsConstructor
public class ResumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "resumeEntity",cascade =CascadeType.DETACH)
    private List<FileEntity> files;


    @Column(name = "is_verify")
    private Boolean isVerify = false;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "gender")
    private String gender;
    @Column(name = "married")
    private Boolean isMarried;
    @Column(name = "education")
    private String education;
    @Column(name = "location")
    private String location;
    @Column(name = "birth_day")
    private String birthday;
    @Column(name = "position")
    private String position;
    @Column(name = "salary")
    private int salary ;
    @Column(name = "skills")
    private String skills;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_date")
    private Date creationDate;
    @Column(name = "status")
    private String status;
}
