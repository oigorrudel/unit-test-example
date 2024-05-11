package br.xksoberbado.unittestexample.repository;

import br.xksoberbado.unittestexample.model.Person;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends ListCrudRepository<Person, UUID> {

    List<Person> findAllByName(String name);
}
