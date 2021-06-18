package com.caffein.repository;

import com.caffein.entity.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ConceptRepository extends JpaRepository<Concept, Long> {
    @Query("SELECT co, ca FROM Concept co LEFT JOIN co.target ca WHERE co.conceptId = :conceptId")
    Object getConceptByConceptId(@Param("conceptId") Long conceptId);
    @Query("SELECT co, ca FROM Concept co LEFT JOIN co.target ca")
    Page<Object[]> getConceptWithTarget(Pageable pageable);
}
