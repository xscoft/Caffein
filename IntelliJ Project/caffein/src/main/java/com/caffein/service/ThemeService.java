package com.caffein.service;

import com.caffein.dto.ThemeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Theme;

public interface ThemeService {

    Long register(ThemeDTO dto);

    PageResultDTO<ThemeDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    default Theme dtoToEntity(ThemeDTO dto) {
        Cafe cafe = Cafe.builder().cafeId(dto.getTargetCafeId()).build();

        Theme entity = Theme.builder()
                .themeId(dto.getThemeId())
                .name(dto.getName())
                .target(cafe)
                .build();
        return entity;
    }

    default ThemeDTO entityToDTO(Theme entity, Cafe cafe) {
        ThemeDTO dto = ThemeDTO.builder()
                .themeId(entity.getThemeId())
                .name(entity.getName())
                .rDate(entity.getRDate())
                .mDate(entity.getMDate())
                .targetCafeId(cafe.getCafeId())
                .targetCafeName(cafe.getName())
                .build();
        return dto;
    }

    ThemeDTO get(Long themeId);

    void remove(Long themeId);

    void modify(ThemeDTO dto);
}
