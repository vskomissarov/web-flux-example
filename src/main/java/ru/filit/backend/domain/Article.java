package ru.filit.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@Table("ARTICLE")
@AllArgsConstructor(staticName = "create")
public class Article extends Auditing {

    /**
     * title
     */
    @NotNull
    private String title;

    /**
     * body
     */
    @NotNull
    @JsonIgnore
    private String body;

    @Transient
    private boolean newArticle;

    @Override
    @Transient
    public boolean isNew() {
        return this.newArticle || this.getId() == null;
    }

    public Article setAsNew() {
        this.newArticle = true;
        this.setCreatedDate(LocalDateTime.now());
        return this;
    }
}
