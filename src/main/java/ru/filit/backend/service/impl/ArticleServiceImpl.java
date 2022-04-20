package ru.filit.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.filit.backend.domain.Article;
import ru.filit.backend.dto.ArticleDto;
import ru.filit.backend.exception.EntityNotFoundException;
import ru.filit.backend.repository.ArticleRepository;
import ru.filit.backend.service.ArticleService;
import ru.filit.backend.utils.ArticleConverter;

import static ru.filit.backend.utils.ArticleConverter.toArticle;
import static ru.filit.backend.utils.ArticleConverter.toArticleDto;
import static ru.filit.backend.utils.PageUtils.getSort;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public Mono<ArticleDto> save(ArticleDto dto) {
        Mono<ArticleDto> articleDtoMono;
        if (dto.getId() != null) {
            articleDtoMono = update(dto);
        } else {
            articleDtoMono = saveNew(dto);
        }
        return articleDtoMono;
    }

    @Override
    @Transactional
    public Mono<ArticleDto> delete(Long id) {
        return articleRepository
                .findById(id)
                .flatMap(article -> articleRepository.deleteById(article.getId())
                        .thenReturn(toArticleDto(article)));
    }

    @Override
    public Mono<ArticleDto> getById(Long id) {
        return articleRepository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(Article.class, id)))
                .map(ArticleConverter::toArticleDto);
    }

    @Override
    public Mono<Page<ArticleDto>> findAll(Integer page, Integer size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, getSort(sort));
        return articleRepository.findAllBy(pageRequest)
                .subscribeOn(Schedulers.boundedElastic())
                .collectList()
                .zipWith(this.articleRepository.count())
                .map(tuple -> new PageImpl<>(ArticleConverter.convertToList(tuple.getT1()), pageRequest, tuple.getT2()));

    }

    private Mono<ArticleDto> saveNew(ArticleDto dto) {
        return articleRepository.save(ArticleConverter.toArticle(new Article(), dto).setAsNew())
                .subscribeOn(Schedulers.boundedElastic())
                .map(ArticleConverter::toArticleDto);
    }

    private Mono<ArticleDto> update(ArticleDto dto) {
        return articleRepository.findById(dto.getId())
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(Article.class, dto.getId())))
                .flatMap(article -> articleRepository.save(toArticle(article, dto)))
                .map(ArticleConverter::toArticleDto);
    }
}
