package ru.filit.backend.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.filit.backend.dto.ArticleDto;
import ru.filit.backend.service.ArticleService;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("{id}")
    public Mono<ArticleDto> getById(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @DeleteMapping("{id}")
    public Mono<ArticleDto> delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    @GetMapping
    public Mono<Page<ArticleDto>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "50") Integer size,
                                          @RequestParam(required = false, defaultValue = "id,desc") String sort) {

        return articleService.findAll(page, size, sort);
    }

    @PostMapping
    public Mono<ArticleDto> save(@Validated @RequestBody ArticleDto dto) {
        return articleService.save(dto);
    }

    @PutMapping
    public Mono<ArticleDto> update(@Validated @RequestBody ArticleDto dto) {
        return articleService.save(dto);
    }
}
