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
import ru.filit.backend.dto.UserDto;
import ru.filit.backend.service.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public Mono<UserDto> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("{id}")
    public Mono<UserDto> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping
    public Mono<Page<UserDto>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "50") Integer size,
                                       @RequestParam(required = false, defaultValue = "id,desc") String sort) {
        return userService.findAll(page, size, sort);
    }

    @PostMapping
    public Mono<UserDto> save(@Validated @RequestBody UserDto dto) {
        return userService.save(dto);
    }

    @PutMapping
    public Mono<UserDto> update(@Validated @RequestBody UserDto dto) {
        return userService.save(dto);
    }
}
