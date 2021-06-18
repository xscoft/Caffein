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
public class ConceptDTO {
    private Long conceptId;
    private String name;
    private LocalDateTime rDate, mDate;
    private Long targetCafeId;
    private String targetCafeName;
}
