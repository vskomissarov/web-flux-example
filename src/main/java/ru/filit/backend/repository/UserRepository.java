package ru.filit.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.filit.backend.domain.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Flux<User> findBy(Pageable pageable);
}
