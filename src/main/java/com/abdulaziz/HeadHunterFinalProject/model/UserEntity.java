package com.abdulaziz.HeadHunterFinalProject.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users",schema = "opa")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name ="surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private RoleEntity role;


    @OneToMany(cascade = CascadeType.DETACH,mappedBy = "user")
    private List<VacancyEntity> vacancyEntity;

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "user")
    private List<ResumeEntity> resumeEntity;
}
