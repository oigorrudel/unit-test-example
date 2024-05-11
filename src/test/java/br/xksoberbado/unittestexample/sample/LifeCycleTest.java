package br.xksoberbado.unittestexample.sample;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifeCycleTest {

    @BeforeAll
    void befAll() {
        System.out.println("Antes de todos!");
    }

    @AfterAll
    void aftAll() {
        System.out.println("Depois de todos!");
    }

    @Nested
    class WhenTest {

        @BeforeEach
        void setup() {
            System.out.println("Antes dos testes do Test!");
        }

        @Test
        void shouldCallCorrectly() {
            assertEquals("1", Integer.valueOf("1").toString());
        }
    }

    @Nested
    class WhenTest2 {

        @BeforeEach
        void setup() {
            System.out.println("Antes dos testes do Test2!");
        }

        @Test
        void shouldCallCorrectly() {
            assertEquals("1", Integer.valueOf("1").toString());
        }

        @Test
        void shouldCallCorrectly2() {
            assertEquals("1", Integer.valueOf("1").toString());
        }
    }
}
