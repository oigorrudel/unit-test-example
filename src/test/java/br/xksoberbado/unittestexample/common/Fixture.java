package br.xksoberbado.unittestexample.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jeasy.random.EasyRandom;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Fixture {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static <T> T make(final Class<T> clazz) {
        return EASY_RANDOM.nextObject(clazz);
    }

    public static <T> List<T> makeList(final Class<T> clazz) {
        return makeList(clazz, 1);
    }

    public static <T> List<T> makeList(final Class<T> clazz, final Integer size) {
        return EASY_RANDOM.objects(clazz, size).toList();
    }
}
