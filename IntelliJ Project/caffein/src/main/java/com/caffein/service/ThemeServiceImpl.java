package com.caffein.service;

import com.caffein.dto.ThemeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Theme;
import com.caffein.repository.ThemeRepository;
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
public class ThemeServiceImpl implements ThemeService{

    private final ThemeRepository repository;

    @Override
    public Long register(ThemeDTO dto) {

        log.info("Theme DTO----------------------");
        log.info(dto);

        Theme entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getThemeId();
    }

    @Override
    public PageResultDTO<ThemeDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("themeId").descending());

        Function<Object[], ThemeDTO> fn = (en -> entityToDTO((Theme) en[0], (Cafe) en[1]));

        Page<Object[]> result = repository.getThemeWithTarget(pageable);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ThemeDTO get(Long themeId) {
        Object result = repository.getThemeByThemeId(themeId);

        Object[] arr = (Object[])result;

        return entityToDTO((Theme)arr[0], (Cafe)arr[1]);
    }

    @Override
    public void remove(Long themeId) {
        repository.deleteById(themeId);
    }

    @Override
    public void modify(ThemeDTO dto) {
        Optional<Theme> result = repository.findById(dto.getThemeId());

        if(result.isPresent()) {
            Theme entity = result.get();

            entity.changeName(dto.getName());

            repository.save(entity);
        }
    }
}
