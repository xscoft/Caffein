package com.caffein.service;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.MemberDTO;
import com.caffein.entity.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public interface MemberService {

    default Member dtoToEntity(MemberDTO dto) {

        Member member = Member.builder()
                .memberId(dto.getMemberId())
                .email(dto.getEmail())
                .auth(dto.getAuth())
                .pw(dto.getPw())
                .nickname(dto.getNickname())
                .tel(dto.getTel())
                .sex(dto.getSex())
                .age(dto.getAge()).build();

        return member;
    }

    default MemberDTO entityToDTO(Member member) {

        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .pw(member.getPw())
                .nickname(member.getNickname())
                .tel(member.getTel())
                .sex(member.getSex())
                .age(member.getAge())
                .auth(member.getAuth())
                .rDate(member.getRDate())
                .mDate(member.getMDate())
                .build();
        return memberDTO;
    }
    Member loadUserByUsername(String email);

    Long register(MemberDTO dto);

    PageResultDTO<MemberDTO, Member> getList(PageRequestDTO requestDTO);

    MemberDTO view(Long memberId);

    void modify(MemberDTO dto);

}
