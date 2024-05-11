package br.xksoberbado.unittestexample.controller.dto;

import br.xksoberbado.unittestexample.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonBody {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthdate;
}
