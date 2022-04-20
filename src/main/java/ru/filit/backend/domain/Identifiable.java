package ru.filit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

/**
 * Базовый класс сущности
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Identifiable implements Persistable<Long> {

    /**
     * Идентификатор сущности
     */
    @Id
    private Long id;
}
