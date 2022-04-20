package ru.filit.backend.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import ru.filit.backend.dto.CategoryDto;

public interface CategoryService {
    Mono<CategoryDto> save(CategoryDto dto);

    Mono<CategoryDto> delete(Long id);

    Mono<CategoryDto> getById(Long id);

    Mono<Page<CategoryDto>> findAll(Integer page, Integer size, String sort);

}
