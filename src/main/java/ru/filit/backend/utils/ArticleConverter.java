package ru.filit.backend.utils;

import lombok.experimental.UtilityClass;
import ru.filit.backend.domain.Article;
import ru.filit.backend.dto.ArticleDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class ArticleConverter {

    public static List<ArticleDto> convertToList(List<Article> articles) {
        return articles.stream()
                .map(ArticleConverter::toArticleDto)
                .collect(Collectors.toList());
    }

    public static ArticleDto toArticleDto(Article article) {
        return ArticleDto.builder()
                .body(article.getBody())
                .title(article.getTitle())
                .id(article.getId())
                .build();
    }

    public static Article toArticle(Article article, ArticleDto dto) {
        article.setBody(dto.getBody());
        article.setTitle(dto.getTitle());

        return article;
    }
}
