package ru.filit.backend.handlers;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.filit.backend.domain.Article;
import ru.filit.backend.dto.ArticleDto;
import ru.filit.backend.service.ArticleService;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ArticleHandler {

    private final ArticleService articleService;

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<ArticleDto> flux = request
                .bodyToFlux(ArticleDto.class)
                .flatMap(toWrite -> this.articleService.save(toWrite));
        return defaultWriteResponse(flux);
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        Flux<Object> listFlux = request
                .bodyToFlux(Pageable.class)
                .flatMap(pageable -> this.articleService.findAll(0, 0, null));
        return defaultReadAllResponse(listFlux);
    }

    public Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.articleService.delete(id(r)));
    }

    public Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.articleService.getById(id(r)));
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<ArticleDto> products) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Article.class);
    }

    private static Mono<ServerResponse> defaultReadAllResponse(Publisher<Object> products) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Article.class);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<ArticleDto> product) {
        return Mono
                .from(product)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/product"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }

    private static Long id(ServerRequest r) {
        return Long.parseLong(r.pathVariable("id"));
    }
}
