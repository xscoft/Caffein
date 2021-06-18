package com.caffein.service;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;

import java.util.List;

public interface CafeService {
    Long register(CafeDTO dto);

    PageResultDTO<CafeDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    default Cafe dtoToEntity(CafeDTO dto) {
        Member member = Member.builder().memberId(dto.getWriterMemberId()).build();

        Cafe cafe = Cafe.builder()
                .cafeId(dto.getCafeId())
                .name(dto.getName())
                .photo(dto.getPhoto())
                .sido(dto.getSido())
                .sigugun(dto.getSigugun())
                .address(dto.getAddress())
                .tel(dto.getTel())
                .parking(dto.getParking())
                .time(dto.getTime())
                .dayoff(dto.getDayoff())
                .menu(dto.getMenu())
                .location(dto.getLocation())
                .ig(dto.getIg())
                .score(dto.getScore())
                .writer(member)
                .build();
        return cafe;
    }

    default CafeDTO entityToDTO(Cafe cafe, Member member) {

        CafeDTO cafeDTO = CafeDTO.builder()
                .cafeId(cafe.getCafeId())
                .name(cafe.getName())
                .photo(cafe.getPhoto())
                .sido(cafe.getSido())
                .sigugun(cafe.getSigugun())
                .address(cafe.getAddress())
                .tel(cafe.getTel())
                .parking(cafe.getParking())
                .time(cafe.getTime())
                .dayoff(cafe.getDayoff())
                .menu(cafe.getMenu())
                .location(cafe.getLocation())
                .ig(cafe.getIg())
                .score(cafe.getScore())
                .rDate(cafe.getRDate())
                .mDate(cafe.getMDate())
                .writerMemberId(member.getMemberId())
                .writerMemberNickname(member.getNickname())
                .build();
        return cafeDTO;
    }

    CafeDTO get(Long cafeId);

    void modify(CafeDTO cafeDTO);

    void updateScore(CafeDTO cafeDTO);

    void remove(Long cafeId);

    List<CafeDTO> getCafeList();
}
