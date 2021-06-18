package com.caffein.service;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;
import com.caffein.repository.CafeRepository;
import com.caffein.repository.MemberRepository;
import com.caffein.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class CafeServiceImpl implements CafeService {

    private final CafeRepository repository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(CafeDTO dto) {
        log.info(dto);
        Cafe cafe = dtoToEntity(dto);
        repository.save(cafe);
        return cafe.getCafeId();
    }

    @Override
    public PageResultDTO<CafeDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], CafeDTO> fn = (en -> entityToDTO((Cafe) en[0], (Member) en[1]));

        Page<Object[]> result = repository.getCafeWithWriter(
                pageRequestDTO.getPageable(Sort.by("cafeId").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public CafeDTO get(Long cafeId) {
        Object result = repository.getCafeByCafeId(cafeId);

        Object[] arr = (Object[])result;

        return entityToDTO((Cafe)arr[0], (Member)arr[1]);
    }

    @Override
    public void modify(CafeDTO cafeDTO) {

        Cafe cafe = repository.getOne(cafeDTO.getCafeId());

        if (cafe != null) {
            cafe.changeName(cafeDTO.getName());
            cafe.changePhoto(cafeDTO.getPhoto());
            cafe.changeTel(cafeDTO.getTel());
            cafe.changeParking(cafeDTO.getParking());
            cafe.changeTime(cafeDTO.getTime());
            cafe.changeDayoff(cafeDTO.getDayoff());
            cafe.changeMenu(cafeDTO.getMenu());
            cafe.changeIg(cafeDTO.getIg());

            repository.save(cafe);
        }
    }

    @Override
    public void updateScore(CafeDTO cafeDTO) {
        Cafe cafe = repository.getOne(cafeDTO.getCafeId());

        if (cafe != null) {
            cafe.changeScore(cafeDTO.getScore());

            repository.save(cafe);
        }
    }

    @Override
    public void remove(Long cafeId) {
        repository.deleteById(cafeId);
    }

    @Override
    public List<CafeDTO> getCafeList() {
        List<Cafe> cafeList = repository.getCafeList();
        List<CafeDTO> cafeDtoList = new ArrayList<CafeDTO>();
        int maxSize;
        if (cafeList.size() < 5) {
            maxSize = cafeList.size();
        } else {
            maxSize = 5;
        }

        for (int i = 0; i < maxSize; i++) {
            Cafe cafe = cafeList.get(i);
            Member member = memberRepository.getById(cafe.getWriter().getMemberId());
            CafeDTO cafeDTO = entityToDTO(cafe, member);
            cafeDtoList.add(cafeDTO);
        }
        return cafeDtoList;
    }


}
