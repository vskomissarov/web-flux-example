package ru.filit.backend.utils;


import lombok.experimental.UtilityClass;
import ru.filit.backend.domain.Category;
import ru.filit.backend.dto.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class CategoryConverter {

    public static List<CategoryDto> convertToList(List<Category> articles) {
        return articles.stream()
                .map(CategoryConverter::toCategoryDto)
                .collect(Collectors.toList());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId())
                .build();
    }

    public static Category toCategory(Category category, CategoryDto dto) {
        category.setName(dto.getName());
        return category;
    }


}
