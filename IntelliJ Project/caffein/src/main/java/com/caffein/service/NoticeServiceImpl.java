package com.caffein.service;

import com.caffein.dto.NoticeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.entity.Notice;
import com.caffein.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository repository;

    @Override
    public Long write(NoticeDTO dto) {

        log.info("Notice DTO----------------------");
        log.info(dto);

        Notice entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getNoticeId();
    }

    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("noticeId").descending());

        Page<Notice> result = repository.findAll(pageable);

        Function<Notice, NoticeDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public NoticeDTO read(Long noticeId) {
        Optional<Notice> result = repository.findById(noticeId);
        return result.isPresent() ? entityToDTO(result.get()) : null;
    }

    @Override
    public void remove(Long noticeId) {
        repository.deleteById(noticeId);
    }

    @Override
    public void modify(NoticeDTO dto) {
        Optional<Notice> result = repository.findById(dto.getNoticeId());

        if(result.isPresent()) {
            Notice entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    @Override
    public List<NoticeDTO> getNoticeList() {
        List<Notice> noticeList = repository.getNoticeList();
        List<NoticeDTO> noticeDtoList = new ArrayList<NoticeDTO>();
        int maxSize;
        if(noticeList.size() < 5) {
            maxSize = noticeList.size();
        } else {
            maxSize = 5;
        }
        for (int i = 0; i < maxSize; i++) {
            Notice notice = noticeList.get(i);
            NoticeDTO noticeDTO = entityToDTO(notice);
            noticeDtoList.add(noticeDTO);
        }
        return noticeDtoList;
    }
}
