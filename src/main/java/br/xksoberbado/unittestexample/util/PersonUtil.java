package br.xksoberbado.unittestexample.util;

import br.xksoberbado.unittestexample.exception.BusinessException;
import br.xksoberbado.unittestexample.model.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PersonUtil {

    public void validateCreate(final Person person) {
        if (!isAdult(person)) {
            throw new BusinessException("Person is not adult.");
        }
    }

    public boolean isAdult(final Person person) {
        return Period.between(person.getBirthdate(), LocalDate.now()).getYears() > 17;
    }
}
