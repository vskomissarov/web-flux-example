package ru.filit.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.filit.backend.domain.User;
import ru.filit.backend.dto.UserDto;
import ru.filit.backend.exception.EntityNotFoundException;
import ru.filit.backend.repository.UserRepository;
import ru.filit.backend.service.UserService;
import ru.filit.backend.utils.UserConverter;

import static ru.filit.backend.utils.PageUtils.getSort;
import static ru.filit.backend.utils.UserConverter.toUserDto;
import static ru.filit.backend.utils.UserConverter.toUserEntry;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDto> save(UserDto dto) {
        Mono<UserDto> userDtoMono;
        if (dto.getId() != null) {
            userDtoMono = update(dto);
        } else {
            userDtoMono = saveNew(dto);
        }
        return userDtoMono;
    }

    @Override
    public Mono<UserDto> delete(Long id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.deleteById(user.getId())
                        .thenReturn(toUserDto(user)));
    }

    @Override
    public Mono<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(User.class, id)))
                .map(UserConverter::toUserDto);
    }

    @Override
    public Mono<Page<UserDto>> findAll(Integer page, Integer size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, getSort(sort));

        return userRepository.findBy(pageRequest)
                .subscribeOn(Schedulers.boundedElastic())
                .collectList()
                .zipWith(this.userRepository.count())
                .map(tuple -> new PageImpl<>(UserConverter.convertToList(tuple.getT1()), pageRequest, tuple.getT2()));
    }

    private Mono<UserDto> saveNew(UserDto dto) {
        return userRepository.save(toUserEntry(new User(), dto).setAsNew())
                .subscribeOn(Schedulers.boundedElastic())
                .map(UserConverter::toUserDto);
    }

    private Mono<UserDto> update(UserDto dto) {
        return userRepository.findById(dto.getId())
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(User.class, dto.getId())))
                .flatMap(user -> userRepository.save(toUserEntry(user, dto)))
                .map(UserConverter::toUserDto);
    }
}
