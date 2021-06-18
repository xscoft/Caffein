package com.caffein.repository;

import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Cafe cafe = Cafe.builder().cafeId((long) i).build();
            Member member = Member.builder().memberId((long) i).build();

            Review review = Review.builder()
                    .score(0)
                    .title("리뷰 제목" + i)
                    .content("리뷰 내용")
                    .photo("리뷰 사진")
                    .target(cafe)
                    .writer(member)
                    .build();
            System.out.println(reviewRepository.save(review));
        });
    }
}
