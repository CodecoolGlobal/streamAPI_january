package service;

import model.Pizza;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PizzaServiceTest {
    static PizzaService pizzaService;

    static Stream<Arguments> pizzaPriceProvider() {
        return Stream.of(
                arguments(13.0, Pizza.MARGHERITA),
                arguments(17.0, Pizza.CAPRI),
                arguments(17.0, Pizza.HAVAI),
                arguments(15.0, Pizza.CARUSO),
                arguments(18.0, Pizza.MAMA_MIA),
                arguments(23.0, Pizza.SOPRANO),
                arguments(24.0, Pizza.CALABRESE),
                arguments(20.0, Pizza.VEGETARIANA),
                arguments(19.0, Pizza.CAPRESE),
                arguments(16.0, Pizza.PASCETORE),
                arguments(19.0, Pizza.FOUR_CHEESE),
                arguments(22.0, Pizza.TABASCO),
                arguments(16.0, Pizza.AMORE),
                arguments(22.0, Pizza.FARMER)
        );
    }

    @ParameterizedTest
    @MethodSource("pizzaPriceProvider")
    void testGetPizzaPriceWithValidEnumValues(double expected, Pizza pizza) {
        assertEquals(expected, pizzaService.getPizzaPrice(pizza));
    }

    @BeforeEach
    void setUp() {
        pizzaService = new PizzaService();
    }

    @AfterEach
    void tearDown() {
        pizzaService = null;
    }

    @Test
    void getPizzaPriceIfCapri() {
        // arrange
        double expected = 17.0;

        // act
        double actual = pizzaService.getPizzaPrice(Pizza.CAPRI);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void getPizzaPriceIfMargherita() {
        // arrange
        double expected = 13.0;

        // act
        double actual = pizzaService.getPizzaPrice(Pizza.MARGHERITA);

        // assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1, 1",
            "4, 2, 2"
    })
    void testAddingPositiveNumbers(int expected, int arg1, int arg2) {
        assertEquals(expected, pizzaService.add(arg1, arg2));
    }

    @Test
    void testAddingPositiveNumbersWithAssertAll() {
        assertAll(
                () -> assertEquals(2, pizzaService.add(1, 1)),
                () -> assertEquals(4, pizzaService.add(2, 2))
        );
    }

    @Test
    void iLikeMeat() {
        // given
        List<Pizza> expected = new ArrayList<>();
        expected.add(Pizza.FARMER);
        expected.add(Pizza.CALABRESE);
        expected.add(Pizza.TABASCO);
        expected.add(Pizza.SOPRANO);
        expected.add(Pizza.MAMA_MIA);
        expected.add(Pizza.CAPRI);
        expected.add(Pizza.CARUSO);
        expected.add(Pizza.HAVAI);
        expected.add(Pizza.AMORE);
        // when
        List<Pizza> actual = pizzaService.iLikeMeat();
        // then
        assertThat(actual).containsExactlyInAnyOrder(expected.toArray(new Pizza[0]));
    }

    @Test
    void groupByPrice() {
        // given
        var expected = new HashMap<>();
        expected.put(16.0, List.of(Pizza.PASCETORE, Pizza.AMORE));
        expected.put(17.0, List.of(Pizza.CAPRI, Pizza.HAVAI));
        expected.put(18.0, List.of(Pizza.MAMA_MIA));
        expected.put(19.0, List.of(Pizza.CAPRESE, Pizza.FOUR_CHEESE));
        expected.put(20.0, List.of(Pizza.VEGETARIANA));
        expected.put(22.0, List.of(Pizza.TABASCO, Pizza.FARMER));
        expected.put(23.0, List.of(Pizza.SOPRANO));
        expected.put(24.0, List.of(Pizza.CALABRESE));
        expected.put(13.0, List.of(Pizza.MARGHERITA));
        expected.put(15.0, List.of(Pizza.CARUSO));
        // when
        var actual = pizzaService.groupByPrice();
        // then
        assertEquals(expected, actual);
    }
}
