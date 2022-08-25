package com.geekbrains.test;

import com.geekbrains.Main;
import org.junit.jupiter.api.*;

public class MainTest {
    private Main main;

    @BeforeEach
    void startUp() {
        System.out.println("начинается тест");
        main = new Main();
    }

    @AfterEach
    void afterEach() {
        System.out.println("завершается тест");
    }

    @AfterAll
    static void mainEnd() {
        System.out.println("ГЛАВНОЕ ОКОНЧАНИЕ");
    }

    @BeforeAll
    static void mainStart() {
        System.out.println("ГЛАВНОЕ НАЧАЛО");
    }

    @DisplayName("1, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 2, 1")
    @Test
    void testMethod1() {
        Assertions.assertArrayEquals(new int[]{2, 1},
                main.testMethod(new int[]{1, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 2, 1}));
    }

    @DisplayName("4, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 4, 1")
    @Test
    void testMethod2() {
        Assertions.assertArrayEquals(new int[]{1},
                main.testMethod(new int[]{4, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 4, 1}));
    }

    @DisplayName("1, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 2, 4")
    @Test
    void testMethod3() {
        Assertions.assertArrayEquals(new int[]{},
                main.testMethod(new int[]{1, 2, 3, 4, 5, 4, 5, 4, 6, 7, 4, 2, 4}));
    }

    @DisplayName("Без четверок")
    @Test
    void testMethod4() {
        Assertions.assertThrows(RuntimeException.class,
                () -> main.testMethod(new int[]{1, 2, 3, 9, 5, 7, 5, 3, 6, 7, 5, 2, 1}));
    }

    @DisplayName("1, 1, 1, 1, 1")
    @Test
    void testBoolMethod1() {
        Assertions.assertFalse(main.testBoolMethod(new int[]{1, 1, 1, 1, 1}));
    }

    @DisplayName("4, 4, 4, 3, 1")
    @Test
    void testBoolMethod2() {
        Assertions.assertFalse(main.testBoolMethod(new int[]{4, 4, 4, 3, 1}));
    }

    @DisplayName("1, 1, 1, 4")
    @Test
    void testBoolMethod3() {
        Assertions.assertTrue(main.testBoolMethod(new int[]{1, 1, 1, 4}));
    }
}
