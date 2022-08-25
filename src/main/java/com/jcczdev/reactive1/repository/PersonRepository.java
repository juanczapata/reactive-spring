package com.jcczdev.reactive1.repository;

import com.jcczdev.reactive1.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> getById(Integer id);
    Flux<Person> getAll();
}
