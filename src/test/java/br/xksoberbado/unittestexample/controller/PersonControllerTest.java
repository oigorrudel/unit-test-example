package br.xksoberbado.unittestexample.controller;

import br.xksoberbado.unittestexample.controller.dto.PersonBody;
import br.xksoberbado.unittestexample.model.Person;
import br.xksoberbado.unittestexample.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static br.xksoberbado.unittestexample.common.Fixture.make;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    private static final String URL = "/v1/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService service;

    @Nested
    class WhenGetPeople {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            mockMvc.perform(
                get(URL + "people")
            ).andExpect(status().isOk());

            verify(service).getAll();
        }

        @Test
        @SneakyThrows
        void shouldReturnOkWithParam() {
            final var name = make(String.class);

            mockMvc.perform(
                get(URL + "people")
                    .param("name", name)
            ).andExpect(status().isOk());

            verify(service).getAllByName(name);
        }
    }

    @Nested
    class WhenGetById {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            final var id = UUID.randomUUID();

            final var person = make(Person.class);
            when(service.getByIdOrThrow(id))
                .thenReturn(person);

            mockMvc.perform(
                    get(URL + "people/{id}", id)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(person.getId().toString()))
                .andExpect(jsonPath("$.name").value(person.getName()));
        }
    }

    @Nested
    class WhenPost {

        @Test
        @SneakyThrows
        void shouldReturnCreated() {
            final var body = make(PersonBody.class);

            mockMvc.perform(
                post(URL + "people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            ).andExpect(status().isCreated());

            verify(service).create(any());
        }

        @ParameterizedTest
        @ArgumentsSource(PersonBodyArgumentsProvider.class)
        @SneakyThrows
        void shouldReturnBadRequest(final PersonBody body) {
            mockMvc.perform(
                post(URL + "people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            ).andExpect(status().isBadRequest());

            verifyNoInteractions(service);
        }

        private static class PersonBodyArgumentsProvider implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                final var withNameNull = make(PersonBody.class);
                withNameNull.setName(null);

                final var withNameEmpty = make(PersonBody.class);
                withNameEmpty.setName("");

                final var withGenderEmpty = make(PersonBody.class);
                withGenderEmpty.setGender(null);

                return Stream.of(
                    Arguments.of(withNameNull),
                    Arguments.of(withNameEmpty),
                    Arguments.of(withGenderEmpty)
                );
            }
        }
    }

    @Nested
    class WhenGetAdults {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            mockMvc.perform(
                get(URL + "adults")
            ).andExpect(status().isOk());

            verify(service).getAdults();
        }
    }
}
