package com.caffein.dto;

import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private int score;
    private String title;
    private String content;
    private String photo;
    private LocalDateTime rDate, mDate;
    private Long targetCafeId;
    private String targetCafeName;
    private Long writerMemberId;
    private String writerMemberNickname;
}
