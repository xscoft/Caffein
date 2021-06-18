package com.caffein.service;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.NoticeDTO;
import com.caffein.entity.Notice;

import java.util.List;

public interface NoticeService {

    Long write(NoticeDTO dto);

    PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);

    default Notice dtoToEntity(NoticeDTO dto) {
        Notice entity = Notice.builder()
                .noticeId(dto.getNoticeId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        return entity;
    }

    default NoticeDTO entityToDTO(Notice entity) {
        NoticeDTO dto = NoticeDTO.builder()
                .noticeId(entity.getNoticeId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .rDate(entity.getRDate())
                .mDate(entity.getMDate())
                .build();
        return dto;
    }

    NoticeDTO read(Long noticeId);

    void remove(Long noticeId);

    void modify(NoticeDTO dto);

    List<NoticeDTO> getNoticeList();
}
