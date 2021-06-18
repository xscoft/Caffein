package com.caffein.service;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.MemberDTO;
import com.caffein.entity.Member;
import com.caffein.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;

    @Override
    public Member loadUserByUsername(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    @Override
    public Long register(MemberDTO dto) {
        dto.setPw(passwordEncoder.encode(dto.getPw()));

        return repository.save(dtoToEntity(dto)).getMemberId();
    }

    @Override
    public MemberDTO view(Long memberId) {
        Optional<Member> result = repository.findById(memberId);
        return result.isPresent() ? entityToDTO(result.get()) : null;
    }

    @Override
    public void modify(MemberDTO dto) {
        Optional<Member> result = repository.findById(dto.getMemberId());
        if(result.isPresent()) {
            Member entity = result.get();
            entity.changeAuth(dto.getAuth());
            repository.save(entity);
        }
    }

    @Override
    public PageResultDTO<MemberDTO, Member> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("memberId").descending());

        Page<Member> result = repository.findAll(pageable);

        Function<Member, MemberDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn);
    }
}