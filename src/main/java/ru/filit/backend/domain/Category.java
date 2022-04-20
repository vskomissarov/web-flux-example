package ru.filit.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table("CATEGORY")
public class Category extends Auditing {

    /**
     * Имя/логин
     */
    @NotNull
    private String name;

    @Transient
    private boolean newCategory;

    @Override
    @Transient
    public boolean isNew() {
        return this.newCategory || this.getId() == null;
    }

    public Category setAsNew() {
        this.newCategory = true;
        this.setCreatedDate(LocalDateTime.now());
        return this;
    }
}
