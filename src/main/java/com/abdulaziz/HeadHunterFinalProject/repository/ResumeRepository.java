package com.abdulaziz.HeadHunterFinalProject.repository;

import com.abdulaziz.HeadHunterFinalProject.dto.SearchDTO;
import com.abdulaziz.HeadHunterFinalProject.model.ResumeEntity;
import com.abdulaziz.HeadHunterFinalProject.model.VacancyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
     List<ResumeEntity> findByIsVerifyIsFalse();

     @Query(value = "SELECT * FROM opa.vacancy vc WHERE " +
             "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
             "and (:status is null or lower(vc.status) like lower(concat('%',:status,'%')))" +
             "and (:user_id is null or vc.user_id = :user_id)",
             nativeQuery = true)
     Page<ResumeEntity> findByParams(
             @Param("location") String location,
             @Param("status") String status,
             @Param("user_id") Long personId,
             Pageable pageRequest);
}
