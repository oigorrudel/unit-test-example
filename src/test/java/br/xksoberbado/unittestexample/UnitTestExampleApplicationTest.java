package br.xksoberbado.unittestexample;

import br.xksoberbado.unittestexample.extension.RedisExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(RedisExtension.class)
class UnitTestExampleApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> UnitTestExampleApplication.main(new String[0]));
    }
}
