package com.abdulaziz.HeadHunterFinalProject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files",schema = "opa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_file_name")
    private String fullFileName;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "resume_id", referencedColumnName = "id")
    private ResumeEntity resumeEntity;
}
