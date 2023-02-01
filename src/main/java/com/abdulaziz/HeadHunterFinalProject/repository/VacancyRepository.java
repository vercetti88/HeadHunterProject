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

    @Query(value = "SELECT * FROM crud.vacancy vc WHERE " +
            "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(vc.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or vc.experience = :experience)" +
            "and (:person_id is null or vc.person_id = :person_id)",
            nativeQuery = true)
    Page<VacancyEntity> findByParams(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("person_id") Long personId,
            Pageable pageRequest);
}
