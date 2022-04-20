package ru.filit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto implements Serializable {
    private static final long serialVersionUID = 299064422131084992L;

    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String body;
}
