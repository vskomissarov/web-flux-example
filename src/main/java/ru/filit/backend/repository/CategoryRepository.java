package ru.filit.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.filit.backend.domain.Category;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {

    Flux<Category> findBy(Pageable pageable);
}
