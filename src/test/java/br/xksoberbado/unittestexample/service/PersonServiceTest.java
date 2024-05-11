package br.xksoberbado.unittestexample.service;

import br.xksoberbado.unittestexample.exception.NotFoundException;
import br.xksoberbado.unittestexample.model.Person;
import br.xksoberbado.unittestexample.repository.PersonRepository;
import br.xksoberbado.unittestexample.util.PersonUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static br.xksoberbado.unittestexample.common.Fixture.make;
import static br.xksoberbado.unittestexample.common.Fixture.makeList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonUtil util;

    @Nested
    class WhenGetByIdOrThrow {

        @Test
        void shouldCallCorrectly() {
            final var id = UUID.randomUUID();

            final var person = make(Person.class);
            when(repository.findById(id))
                .thenReturn(Optional.of(person));

            final var result = service.getByIdOrThrow(id);
            assertEquals(person.getId(), result.getId());
            assertEquals(person.getName(), result.getName());
            assertEquals(person.getGender(), result.getGender());
            assertEquals(person.getBirthdate(), result.getBirthdate());
        }

        @Test
        void shouldThrow() {
            final var id = UUID.randomUUID();

            when(repository.findById(id))
                .thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.getByIdOrThrow(id))
                .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    class WhenGetAll {

        @Test
        void shouldCallCorrectly() {
            final var expected = makeList(Person.class);
            when(repository.findAll())
                .thenReturn(expected);

            final var result = service.getAll();
            assertEquals(1, result.size());
            assertFalse(result.isEmpty());
            assertThat(result).containsAll(expected);
        }
    }

    @Nested
    class WhenCreate {

        @Captor
        private ArgumentCaptor<Person> personCaptor;

        @Test
        void shouldCallCorrectly() {
            final var person = make(Person.class);
            person.setId(null);

            doNothing()
                .when(util)
                .validateCreate(person);

            final var expected = make(Person.class);
            when(repository.save(personCaptor.capture()))
                .thenReturn(expected);

            final var result = service.create(person);
            assertEquals(expected.getId(), result.getId());

            final var captured = personCaptor.getValue();
            assertNotNull(captured.getId());
        }
    }

    @Nested
    class WhenGetAdults {

        @Test
        void shouldCallCorrectly() {
            final var people = makeList(Person.class, 5);
            when(repository.findAll())
                .thenReturn(people);

            when(util.isAdult(any()))
                .thenReturn(true);

            final var result = service.getAdults();
            assertEquals(5, result.size());
        }

        @Test
        void shouldCallCorrectly2() {
            final var people = makeList(Person.class, 5);
            when(repository.findAll())
                .thenReturn(people);

            when(util.isAdult(any()))
                .thenReturn(true)
                .thenReturn(false);

            final var result = service.getAdults();
            assertEquals(1, result.size());

            verify(util, times(5)).isAdult(any());
        }
    }

    @Nested
    class WhenGetAllByName {

        @Test
        void shouldCallCorrectly() {
            final var name = make(String.class);

            final var expected = makeList(Person.class);
            when(repository.findAllByName(name))
                .thenReturn(expected);

            final var result = service.getAllByName(name);
            assertEquals(1, result.size());
            assertFalse(result.isEmpty());
            assertThat(result).containsAll(expected);
        }
    }
}
