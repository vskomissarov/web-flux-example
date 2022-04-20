package ru.filit.backend.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import ru.filit.backend.dto.ArticleDto;

public interface ArticleService {
    Mono<ArticleDto> save(ArticleDto dto);

    Mono<ArticleDto> delete(Long id);

    Mono<ArticleDto> getById(Long id);

    Mono<Page<ArticleDto>> findAll(Integer page, Integer size, String sort);
}
