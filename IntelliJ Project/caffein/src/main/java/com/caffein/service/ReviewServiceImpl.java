package com.caffein.service;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;
import com.caffein.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Override
    public Long write(ReviewDTO dto) {
        log.info(dto);
        Review review = dtoToEntity(dto);
        repository.save(review);
        return review.getReviewId();
    }

    @Override
    public PageResultDTO<ReviewDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], ReviewDTO> fn = (en -> entityToDTO((Review) en[0], (Cafe) en[1], (Member) en[2]));

        Page<Object[]> result = repository.getReviewWithTargetAndWriter(
                pageRequestDTO.getPageable(Sort.by("reviewId").descending()));

        return new PageResultDTO<>(result, fn);
    }


    @Override
    public PageResultDTO<ReviewDTO, Object[]> getListByCafeId(PageRequestDTO pageRequestDTO, Long targetCafeId) {
        Function<Object[], ReviewDTO> fn = (en -> entityToDTO((Review) en[0], (Cafe) en[1], (Member) en[2]));

        Page<Object[]> result = repository.getReviewsByTargetCafeId(targetCafeId,
                pageRequestDTO.getPageable(Sort.by("reviewId").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public List<Review> getReviewsByCafeId(Long cafeId) {
        return repository.getReviewsByCafeId(cafeId);
    }

    @Override
    public List<ReviewDTO> getReviewsByCafeIdLimit3(Long cafeId) {
        int limit;
        List<Review> reviewList = repository.getReviewsByCafeId(cafeId);
        List<ReviewDTO> reviewDTOList = new ArrayList<ReviewDTO>();
        if (reviewList.size() >= 3) {
            limit = 3;
        } else {
            limit = reviewList.size();
        }
        for (int i = 0; i < limit; i++) {
            ReviewDTO reviewDTO = ReviewDTO.builder().reviewId(reviewList.get(i).getReviewId())
                    .score(reviewList.get(i).getScore())
                    .title(reviewList.get(i).getTitle())
                    .writerMemberNickname(reviewList.get(i).getWriter().getNickname())
                    .rDate(reviewList.get(i).getRDate())
                    .photo(reviewList.get(i).getPhoto())
                    .content(reviewList.get(i).getContent())
                    .build();
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    @Override
    public ReviewDTO get(Long reviewId) {
        Object result = repository.getReviewByReviewId(reviewId);

        Object[] arr = (Object[])result;

        return entityToDTO((Review)arr[0], (Cafe)arr[1], (Member)arr[2]);
    }

    @Override
    public void remove(Long reviewId) {
        repository.deleteById(reviewId);
    }
}
