package com.jcczdev.reactive1.repository;

import com.jcczdev.reactive1.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Test
    void getAllIdUsingBlockFirst() {
        Flux<Person> personFlux = repository.getAll();
        System.out.println("Flux blockFirst: ".concat(personFlux.blockFirst().toString()));
    }

    @Test
    void getAllUsingSubscribe() {
        Flux<Person> personFlux = repository.getAll();
        personFlux.subscribe(person -> {
            System.out.println("flux subscribe: ".concat(person.toString()));
        });
    }

    @Test
    void getAllUsingCollectList() {
        Flux<Person> personFlux = repository.getAll();
        Mono<List<Person>> listMono = personFlux.collectList();
        listMono.subscribe(people -> {
            people.forEach(person -> System.out.println("Flux with collectList: ".concat(person.toString())));
        });
    }

    @Test
    void testFluxPersonById() {
        Flux<Person> personFlux = repository.getAll();
        final Integer id = 1;
        Mono<Person> monoPerson = personFlux.filter(person -> person.getId() == id).next();
        monoPerson.subscribe(person -> System.out.println("Flux filtering: ".concat(person.toString())));
    }

    @Test
    void testFluxPersonHandlingException() {
        Flux<Person> personFlux = repository.getAll();
        final Integer id = 20;
        Mono<Person> monoPerson = personFlux.filter(person -> person.getId() == id).single();
        monoPerson.doOnError(throwable -> System.out.println("Exception Thrown"))
                .onErrorReturn(Person.builder().build())
                .subscribe(person -> System.out.println("Wihtout exception: ".concat(person.toString())));
    }
}