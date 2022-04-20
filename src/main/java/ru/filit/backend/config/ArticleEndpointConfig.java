package ru.filit.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.filit.backend.handlers.ArticleHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ArticleEndpointConfig {


    private static final String ARTICLES = "/test";

    @Bean
    RouterFunction<ServerResponse> routes(ArticleHandler handler) {
        return route(POST(ARTICLES), handler::create)
                .andRoute(DELETE(ARTICLES + "/{id}"), handler::deleteById)
                .andRoute(GET(ARTICLES + "/{id}"), handler::getById)
                .andRoute(GET(ARTICLES), handler::all);
    }
}
