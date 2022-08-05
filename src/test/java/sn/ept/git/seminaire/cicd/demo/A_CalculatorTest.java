package sn.ept.git.seminaire.cicd.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class A_CalculatorTest {

    private static ICalculator calculator;
    private  double a,b,result, expected;
    private static  Random r ;


    @BeforeAll
    static void beforeAll(){
        log.info("Something to do before all tests");
        calculator = new Calculator();
        r = new Random();
    }

    @BeforeEach
      void beforeEach(){
        log.info("Something to do before each test");
        a = 11;
        b = 22;
    }

    @Test
    void testAdd() {
        //preparation
        expected =a+b;

        //action
        result = calculator.add(a,b);


        //verification
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testSubtract() {
        expected =a-b;

        result = calculator.subtract(a,b);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testMultiply() {
        expected =a*b;

        result = calculator.multiply(a,b);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDivide() {

        expected =a/b;

        result = calculator.divide(a,b);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void divisionByZeroShouldThrowError() {
        b=0;
        assertThrows(
                ArithmeticException.class,
                () -> calculator.divide(a,b)
        );
    }



    @RepeatedTest(value = 3)
    void testAddRepeated() {
        expected =a*2;
        result = calculator.add(a,a);
        assertThat(result).isEqualTo(expected);
    }



    static Stream<Arguments> addTestData() {
        return IntStream
                .range(1,10)
                .mapToObj(item->Arguments.of(r.nextDouble(), r.nextDouble()));
    }

    @DisplayName("ICalculator: parameterized test for add method")
    @ParameterizedTest
    @MethodSource("addTestData")
    void addWithRandomInputsShouldReturnCorrectValue(double a, double b) {
        expected = a+b;
        double result = calculator.add(a,b);
        assertThat(result).isEqualTo(expected);
    }


    @Disabled
    @Test
    void testDisabled() {
    }

}