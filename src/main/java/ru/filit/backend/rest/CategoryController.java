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
import ru.filit.backend.dto.CategoryDto;
import ru.filit.backend.service.CategoryService;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("{id}")
    public Mono<CategoryDto> getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @DeleteMapping("{id}")
    public Mono<CategoryDto> delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    @GetMapping
    public Mono<Page<CategoryDto>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "50") Integer size,
                                           @RequestParam(required = false, defaultValue = "id,desc") String sort) {
        return categoryService.findAll(page, size, sort);
    }

    @PostMapping
    public Mono<CategoryDto> save(@Validated @RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    @PutMapping
    public Mono<CategoryDto> update(@Validated @RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }
}
