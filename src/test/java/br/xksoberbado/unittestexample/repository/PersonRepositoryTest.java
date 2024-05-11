package br.xksoberbado.unittestexample.repository;

import br.xksoberbado.unittestexample.extension.RedisExtension;
import br.xksoberbado.unittestexample.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import static br.xksoberbado.unittestexample.common.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataRedisTest
@ExtendWith(RedisExtension.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Nested
    class WhenFindAllByName {

        @Test
        void shouldReturnNothing() {
            final var name = "Maria";

            final var result = repository.findAllByName(name);

            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturn() {
            final var name = "Maria";

            final var person = make(Person.class);
            person.setName(name);
            repository.save(person);

            final var result = repository.findAllByName(name);

            assertFalse(result.isEmpty());
        }
    }
}
