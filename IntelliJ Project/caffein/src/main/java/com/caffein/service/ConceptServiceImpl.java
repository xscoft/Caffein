package com.caffein.service;

import com.caffein.dto.ConceptDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Concept;
import com.caffein.entity.Member;
import com.caffein.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService{

    private final ConceptRepository repository;

    @Override
    public Long register(ConceptDTO dto) {

        log.info("Concept DTO----------------------");
        log.info(dto);

        Concept entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getConceptId();
    }

    @Override
    public PageResultDTO<ConceptDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("conceptId").descending());

        Function<Object[], ConceptDTO> fn = (en -> entityToDTO((Concept) en[0], (Cafe) en[1]));

        Page<Object[]> result = repository.getConceptWithTarget(pageable);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ConceptDTO get(Long conceptId) {
        Object result = repository.getConceptByConceptId(conceptId);

        Object[] arr = (Object[])result;

        return entityToDTO((Concept)arr[0], (Cafe)arr[1]);
    }

    @Override
    public void remove(Long conceptId) {
        repository.deleteById(conceptId);
    }

    @Override
    public void modify(ConceptDTO dto) {
        Optional<Concept> result = repository.findById(dto.getConceptId());

        if(result.isPresent()) {
            Concept entity = result.get();

            entity.changeName(dto.getName());

            repository.save(entity);
        }
    }
}
