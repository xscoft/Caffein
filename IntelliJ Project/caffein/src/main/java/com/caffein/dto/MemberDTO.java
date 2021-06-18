package com.caffein.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
public class MemberDTO {
    private Long memberId;
    private String email;
    private String pw;
    private String nickname;
    private String tel;
    private int sex;
    private int age;
    private String auth;
    private LocalDateTime rDate, mDate;
}
