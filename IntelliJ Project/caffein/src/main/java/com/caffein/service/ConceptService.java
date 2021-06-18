package com.caffein.service;

import com.caffein.dto.ConceptDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Concept;

public interface ConceptService {

    Long register(ConceptDTO dto);

    PageResultDTO<ConceptDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    default Concept dtoToEntity(ConceptDTO dto) {
        Cafe cafe = Cafe.builder().cafeId(dto.getTargetCafeId()).build();

        Concept entity = Concept.builder()
                .conceptId(dto.getConceptId())
                .name(dto.getName())
                .target(cafe)
                .build();
        return entity;
    }

    default ConceptDTO entityToDTO(Concept entity, Cafe cafe) {
        ConceptDTO dto = ConceptDTO.builder()
                .conceptId(entity.getConceptId())
                .name(entity.getName())
                .rDate(entity.getRDate())
                .mDate(entity.getMDate())
                .targetCafeId(cafe.getCafeId())
                .targetCafeName(cafe.getName())
                .build();
        return dto;
    }

    ConceptDTO get(Long conceptId);

    void remove(Long conceptId);

    void modify(ConceptDTO dto);
}
