package ru.filit.backend.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import ru.filit.backend.dto.UserDto;

public interface UserService {
    Mono<UserDto> save(UserDto dto);

    Mono<UserDto> delete(Long id);

    Mono<UserDto> getById(Long id);

    Mono<Page<UserDto>> findAll(Integer page, Integer size, String sort);
}
