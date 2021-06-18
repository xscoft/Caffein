package com.caffein.repository;

import com.caffein.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("이메일" + i)
                    .pw(passwordEncoder.encode("1234"))
                    .nickname("닉네임" + i)
                    .tel("연락처" + i)
                    .auth("ROLE_MEMBER")
                    .build();

            System.out.println(memberRepository.save(member));
        });
        IntStream.rangeClosed(101, 101).forEach(i -> {

            Member member = Member.builder()
                    .email("admin")
                    .pw(passwordEncoder.encode("1234"))
                    .nickname("admin")
                    .tel("admin")
                    .auth("ROLE_ADMIN")
                    .build();

            System.out.println(memberRepository.save(member));
        });
    }
}
