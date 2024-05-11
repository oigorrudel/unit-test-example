package br.xksoberbado.unittestexample.util;

import br.xksoberbado.unittestexample.exception.BusinessException;
import br.xksoberbado.unittestexample.model.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static br.xksoberbado.unittestexample.common.Fixture.make;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonUtilTest {

    @InjectMocks
    private PersonUtil util;

    @Nested
    class WhenValidateCreate {

        @Test
        void shouldCallCorrectly() {
            final var person = make(Person.class);
            person.setBirthdate(LocalDate.now().minusYears(18));

            assertDoesNotThrow(() -> util.validateCreate(person));
        }

        @Test
        void shouldThrow() {
            final var person = make(Person.class);
            person.setBirthdate(LocalDate.now().minusYears(17));

            assertThatThrownBy(() -> util.validateCreate(person))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Person is not adult.");
        }

        @Test
        void shouldThrow2() {
            final var person = make(Person.class);
            person.setBirthdate(LocalDate.now().minusYears(17));

            final var exception = assertThrows(
                BusinessException.class,
                () -> util.validateCreate(person)
            );

            assertEquals("Teest", exception.getSecondMessage());
        }
    }
}
