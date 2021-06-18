package com.caffein.service;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;

import java.util.List;

public interface ReviewService {
    Long write(ReviewDTO reviewDTO);

    PageResultDTO<ReviewDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    PageResultDTO<ReviewDTO, Object[]> getListByCafeId(PageRequestDTO pageRequestDTO, Long targetCafeId);

    List<Review> getReviewsByCafeId(Long cafeId);

    List<ReviewDTO> getReviewsByCafeIdLimit3(Long cafeId);

    default Review dtoToEntity(ReviewDTO dto) {
        Cafe cafe = Cafe.builder().cafeId(dto.getTargetCafeId()).build();
        Member member = Member.builder().memberId(dto.getWriterMemberId()).build();

        Review review = Review.builder()
                .reviewId(dto.getReviewId())
                .score(dto.getScore())
                .title(dto.getTitle())
                .content(dto.getContent())
                .photo(dto.getPhoto())
                .target(cafe)
                .writer(member)
                .build();
        return review;
    }

    default ReviewDTO entityToDTO(Review review, Cafe cafe, Member member) {

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .score(review.getScore())
                .title(review.getTitle())
                .content(review.getContent())
                .photo(review.getPhoto())
                .rDate(review.getRDate())
                .mDate(review.getMDate())
                .targetCafeId(cafe.getCafeId())
                .targetCafeName(cafe.getName())
                .writerMemberId(member.getMemberId())
                .writerMemberNickname(member.getNickname())
                .build();
        return reviewDTO;
    }

    ReviewDTO get(Long reviewId);

    void remove(Long reviewId);

}
