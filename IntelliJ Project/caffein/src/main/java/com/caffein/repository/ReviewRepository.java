package com.caffein.repository;

import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, t, w FROM Review r LEFT JOIN r.target t LEFT JOIN r.writer w WHERE r.reviewId = :reviewId")
    Object getReviewByReviewId(@Param("reviewId") Long reviewId);

    @Query("SELECT r, t, w FROM Review r LEFT JOIN r.target t LEFT JOIN r.writer w")
    Page<Object[]> getReviewWithTargetAndWriter(Pageable pageable);

    @Query("SELECT r, t, w FROM Review r LEFT JOIN r.target t LEFT Join r.writer w WHERE r.target.cafeId = :targetCafeId")
    Page<Object[]> getReviewsByTargetCafeId(@Param("targetCafeId") Long targetCafeId, Pageable pageable);

    @Query("SELECT r, t, w FROM Review r LEFT JOIN r.target t LEFT Join r.writer w WHERE r.target.cafeId = :targetCafeId ORDER BY r.reviewId DESC")
    List<Review> getReviewsByCafeId(@Param("targetCafeId") Long targetCafeId);
}
