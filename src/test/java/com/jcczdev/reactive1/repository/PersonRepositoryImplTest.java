package com.jcczdev.reactive1.repository;

import com.jcczdev.reactive1.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryImplTest {
    PersonRepository repository;

    @BeforeEach
    void setup() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void getByIdUsingBlock() {
        Mono<Person> personMono = repository.getById(1);
        Person person = personMono.block();

        assertThat(person).hasToString("Person(id=1, firstName=name1, lastName=lastName1)");
    }

    @Test
    void getByIdUsingSubscribe() {
        Mono<Person> personMono = repository.getById(1);
        personMono.subscribe(person -> System.out.println("From subscribe: ".concat(person.toString())));
    }

    @Test
    void getByIdUsingFunction() {
        Mono<Person> personMono = repository.getById(1);
        personMono.map(person -> person.getFirstName())
                .subscribe(firstName -> System.out.println("From function: ".concat(firstName)));
    }
}