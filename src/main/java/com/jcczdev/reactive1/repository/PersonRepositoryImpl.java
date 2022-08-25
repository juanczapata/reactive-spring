package com.jcczdev.reactive1.repository;

import com.jcczdev.reactive1.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {
    Person person1 = new Person(1, "name1", "lastName1");
    Person person2 = new Person(2, "name2", "lastName2");
    Person person3 = new Person(3, "name3", "lastName3");
    Person person4 = new Person(4, "name4", "lastName4");

    @Override
    public Mono<Person> getById(Integer id) {
        Flux<Person> personsFlux = getAll();
        return personsFlux.filter(person -> person.getId() == id).single();
    }

    @Override
    public Flux<Person> getAll() {
        return Flux.just(person1, person2, person3, person4);
    }
}
