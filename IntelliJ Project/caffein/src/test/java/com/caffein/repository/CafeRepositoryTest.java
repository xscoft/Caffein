package com.caffein.repository;

import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CafeRepositoryTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().memberId((long) i).build();

            Cafe cafe = Cafe.builder()
                    .name("카페이름" + i)
                    .photo("photo")
                    .sido("sido")
                    .sigugun("sigugun")
                    .address("address")
                    .tel("tel")
                    .parking(1)
                    .time("time")
                    .dayoff("dayoff")
                    .menu("menu")
                    .location("location")
                    .ig("ig")
                    .score(0)
                    .writer(member)
                    .build();
            System.out.println(cafeRepository.save(cafe));
        });
    }
}
