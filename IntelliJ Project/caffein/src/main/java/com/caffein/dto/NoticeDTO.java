package com.caffein.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDTO {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime rDate, mDate;
}
