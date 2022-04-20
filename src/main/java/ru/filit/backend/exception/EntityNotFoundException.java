package ru.filit.backend.exception;


import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7409420264622605036L;

    public EntityNotFoundException(Class clazz, Long id) {
        super(format("Entity not found '%s' '%s'", clazz.getName(), id.toString()));
    }
}
