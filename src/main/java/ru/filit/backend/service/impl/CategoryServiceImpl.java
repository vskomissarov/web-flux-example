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
import ru.filit.backend.domain.Category;
import ru.filit.backend.dto.CategoryDto;
import ru.filit.backend.exception.EntityNotFoundException;
import ru.filit.backend.repository.CategoryRepository;
import ru.filit.backend.service.CategoryService;
import ru.filit.backend.utils.CategoryConverter;

import static ru.filit.backend.utils.CategoryConverter.toCategory;
import static ru.filit.backend.utils.CategoryConverter.toCategoryDto;
import static ru.filit.backend.utils.PageUtils.getSort;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Mono<CategoryDto> save(CategoryDto dto) {
        Mono<CategoryDto> categoryMono;
        if (dto.getId() != null) {
            categoryMono = update(dto);
        } else {
            categoryMono = saveNewEntity(dto);
        }
        return categoryMono;
    }

    @Override
    public Mono<CategoryDto> delete(Long id) {
        return categoryRepository.findById(id)
                .flatMap(category -> categoryRepository.deleteById(category.getId())
                        .thenReturn(toCategoryDto(category)));
    }

    @Override
    public Mono<CategoryDto> getById(Long id) {
        return categoryRepository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(Category.class, id)))
                .map(CategoryConverter::toCategoryDto);
    }

    @Override
    public Mono<Page<CategoryDto>> findAll(Integer page, Integer size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, getSort(sort));
        return categoryRepository.findBy(pageRequest)
                .subscribeOn(Schedulers.boundedElastic())
                .collectList()
                .zipWith(this.categoryRepository.count())
                .map(tuple -> new PageImpl<>(CategoryConverter.convertToList(tuple.getT1()), pageRequest, tuple.getT2()));
    }

    private Mono<CategoryDto> saveNewEntity(CategoryDto dto) {
        return categoryRepository.save(toCategory(new Category(), dto).setAsNew())
                .subscribeOn(Schedulers.boundedElastic())
                .map(CategoryConverter::toCategoryDto);
    }

    private Mono<CategoryDto> update(CategoryDto dto) {
        return categoryRepository.findById(dto.getId())
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(Article.class, dto.getId())))
                .flatMap(category -> categoryRepository.save(toCategory(category, dto)))
                .map(CategoryConverter::toCategoryDto);
    }
}
