package com.abdulaziz.HeadHunterFinalProject.repository;

import com.abdulaziz.HeadHunterFinalProject.model.VacancyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VacancyRepository extends JpaRepository<VacancyEntity, Long> {
    List<VacancyEntity> findByIsVerifyIsFalse();

    @Query(value = "SELECT * FROM opa.vacancy vc WHERE " +
            "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
            "and (:job_title is null or lower(vc.job_title) like lower(concat('%',:job_title,'%')))" +
//            "and (:status is null or vc.status = :status)" +
            "and (:user_id is null or vc.user_id = :user_id)",
            nativeQuery = true)
    Page<VacancyEntity> findByParams(
            @Param("location") String location,
            @Param("job_title") String jobTitle,
//            @Param("status") String status,
            @Param("user_id") Long personId,
            Pageable pageRequest);
}
