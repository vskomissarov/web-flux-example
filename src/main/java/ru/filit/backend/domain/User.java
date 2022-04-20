package ru.filit.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Пользователь
 */
@Getter
@Setter
@NoArgsConstructor
@Table("USERS")
public class User extends Auditing {

    /**
     * Имя/логин
     */
    @NotNull
    private String userName;

    /**
     * Пароль
     */
    @NotNull
    @JsonIgnore
    private String password;

    /**
     * Мыло
     */
    @NotNull
    private String email;

    @Transient
    private boolean newUser;

    @Override
    @Transient
    public boolean isNew() {
        return this.newUser || this.getId() == null;
    }

    public User setAsNew() {
        this.newUser = true;
        this.setCreatedDate(LocalDateTime.now());
        return this;
    }
}
