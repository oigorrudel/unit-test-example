package br.xksoberbado.unittestexample.model;

import br.xksoberbado.unittestexample.domain.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@RedisHash("people")
@EqualsAndHashCode
public class Person {

    @Id
    private UUID id;

    @Indexed
    private String name;

    private Gender gender;

    private LocalDate birthdate;
}
