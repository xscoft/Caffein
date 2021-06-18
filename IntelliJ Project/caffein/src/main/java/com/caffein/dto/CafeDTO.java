package com.caffein.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CafeDTO {

    private Long cafeId;
    private String name;
    private String photo;
    private String sido;
    private String sigugun;
    private String address;
    private String tel;
    private int parking;
    private String time;
    private String dayoff;
    private String menu;
    private String location;
    private String ig;
    private int score;
    private LocalDateTime rDate, mDate;
    private Long writerMemberId;
    private String writerMemberNickname;
}
