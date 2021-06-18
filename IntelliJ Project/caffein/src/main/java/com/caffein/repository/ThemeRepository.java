package com.caffein.repository;

import com.caffein.entity.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("SELECT t, c FROM Theme t LEFT JOIN t.target c WHERE t.themeId = :themeId")
    Object getThemeByThemeId(@Param("themeId") Long themeId);
    @Query("SELECT t, c FROM Theme t LEFT JOIN t.target c")
    Page<Object[]> getThemeWithTarget(Pageable pageable);
}
