package br.xksoberbado.unittestexample;

import br.xksoberbado.unittestexample.domain.Gender;
import br.xksoberbado.unittestexample.model.Person;
import br.xksoberbado.unittestexample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class UnitTestExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestExampleApplication.class, args);
    }

    @Autowired
    private PersonRepository repository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            repository.deleteAll();

            final var p1 = Person.of(UUID.fromString("1fb5f084-86d9-4593-a38b-5c4d18f27d45"), "João", Gender.MALE, LocalDate.now().minusYears(17));
            final var p2 = Person.of(UUID.randomUUID(), "Pedro", Gender.MALE, LocalDate.now().minusYears(24));
            final var p3 = Person.of(UUID.randomUUID(), "Maria", Gender.FEMALE, LocalDate.now().minusYears(22));
            final var p4 = Person.of(UUID.randomUUID(), "Joana", Gender.FEMALE, LocalDate.now().minusYears(30));

            repository.saveAll(List.of(p1, p2, p3, p4));
        };
    }
}
