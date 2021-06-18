package com.caffein.repository;

import com.caffein.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {
    @Query("SELECT n FROM Notice n ORDER BY n.noticeId DESC")
    List<Notice> getNoticeList();
}
