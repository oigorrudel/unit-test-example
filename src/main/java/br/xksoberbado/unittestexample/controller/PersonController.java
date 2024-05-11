package br.xksoberbado.unittestexample.controller;

import br.xksoberbado.unittestexample.controller.dto.PersonBody;
import br.xksoberbado.unittestexample.model.Person;
import br.xksoberbado.unittestexample.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping("people")
    public List<Person> get(@RequestParam(required = false) final String name) {
        return Optional.ofNullable(name)
            .map(n -> service.getAllByName(name))
            .orElseGet(service::getAll);
    }

    @GetMapping("people/{id}")
    public Person getById(@PathVariable final UUID id) {
        return service.getByIdOrThrow(id);
    }

    @PostMapping("people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person post(@RequestBody @Valid final PersonBody body) {
        return service.create(
            Person.of(null, body.getName(), body.getGender(), body.getBirthdate())
        );
    }

    @GetMapping("adults")
    public List<Person> getAdults() {
        return service.getAdults();
    }
}
