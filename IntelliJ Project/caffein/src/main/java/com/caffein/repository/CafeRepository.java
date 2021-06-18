package com.caffein.repository;

import com.caffein.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    @Query("SELECT c, w FROM Cafe c LEFT JOIN c.writer w WHERE c.cafeId = :cafeId")
    Object getCafeByCafeId(@Param("cafeId") Long cafeId);
    @Query("SELECT c, w FROM Cafe c LEFT JOIN c.writer w")
    Page<Object[]> getCafeWithWriter(Pageable pageable);
    @Query("SELECT c, w FROM Cafe c LEFT JOIN c.writer w ORDER BY c.cafeId DESC")
    List<Cafe> getCafeList();
}
