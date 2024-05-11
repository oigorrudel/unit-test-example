package br.xksoberbado.unittestexample.service;

import br.xksoberbado.unittestexample.exception.NotFoundException;
import br.xksoberbado.unittestexample.model.Person;
import br.xksoberbado.unittestexample.repository.PersonRepository;
import br.xksoberbado.unittestexample.util.PersonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;
    private final PersonUtil util;

    public Person getByIdOrThrow(final UUID id) {
        return repository.findById(id)
            .orElseThrow(NotFoundException::new);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person create(final Person person) {
        util.validateCreate(person);

        person.setId(UUID.randomUUID());

        return repository.save(person);
    }

    public List<Person> getAdults() {
        return repository.findAll()
            .stream()
            .filter(util::isAdult)
            .toList();
    }

    public List<Person> getAllByName(final String name) {
        return repository.findAllByName(name);
    }
}
