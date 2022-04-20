package ru.filit.backend.utils;

import lombok.experimental.UtilityClass;
import ru.filit.backend.domain.User;
import ru.filit.backend.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class UserConverter {

    public static List<UserDto> convertToList(List<User> users) {
        return users.stream()
                .map(UserConverter::toUserDto)
                .collect(Collectors.toList());
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .id(user.getId())
                .build();
    }


    public static User toUserEntry(User user, UserDto dto) {
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(CryptUtils.cryptWithMD5(dto.getPassword()));

        return user;
    }
}
