package sn.ept.git.seminaire.cicd.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import sn.ept.git.seminaire.cicd.demo.exception.BadPhoneException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

@Slf4j
class E_FirstPrincipalTest {

    public static final String ORANGE = "ORANGE";
    public static final String FREE = "FREE";
    public static final String EXPRESSO = "EXPRESSO";
    public static final String PROMOBILE = "PROMOBILE";

    private static ICalculator calculator;
    private static double resultOne, resultTwo;
    private double a, b;

    @BeforeAll
    static void beforeAll() {
        log.info("Something to do before all tests");
        calculator = new Calculator();
        resultOne = 0;
    }

    @BeforeEach
    void beforeEach() {
        log.info("Something to do before each test");
        a = 11;
        b = 22;
    }


    /**
     * A developer should not hesitate to run the tests as they are slow.
     * You should be aiming for many hundreds or thousands of tests per second.
     *
     * => Avoid depending on network or external services
     */
    @Nested
    class Fast {

        @RepeatedTest(1000)
        void addShouldReturnTheSumOfPositiveNumbers() {
            double provided = calculator.add(b, a);
            double expected = a + b;
            assertThat(provided).isEqualTo(expected);
        }
    }

    /**
     * You can isolate them from interfering with one another
     * No order-of-run dependency => They should pass or fail the same way in suite or when run individually.
     *
     * => not test should prepare data for others
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Isolation {


        @Order(0)
        @Test
        void addShouldReturnTheSumOfTwoPositiveNumbers() {
            // un comment the following line ot change order of running
             //org.assertj.core.api.Assertions.fail("volontairement");
            resultOne = calculator.add(a, b); //resultOne =33
            assertThat(resultOne).isEqualTo(33);
        }

        @Order(1)
        @Test
        void givenTwoPositiveIntegers_whenMultiply_thenCorrectResult() {
            //resultOne = 33
            resultOne = calculator.multiply(a, resultOne);
            //resultOne = 11*33 = 363
            assertThat(resultOne).isEqualTo(363);
        }
    }

    /**
     * No matter how often or where you run it, it should produce the same result (Deterministic results ).
     * Each test should set up or arrange its own data.
     *
     * ===>  What if a set of tests need some common data? Use Data Helper classes that can setup this data for re-usability.
     *
     */
    @Nested
    class Repeatable {

        List<String> lines = Arrays.asList("Hello", "all");
        MyFileReader fileReader = Mockito.mock(MyFileReader.class);

        @BeforeEach
        void beforeEach() throws IOException {
            Mockito
                    .when(fileReader.read(ArgumentMatchers.anyString()))
                    .thenReturn(lines);
        }

        @Test
        void addShouldReturnLinesOfAGivenFile() throws IOException {
            String path ="my_file.txt";

            List<String> result = fileReader.read(path);

            assertThat(result)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(lines.size())
                    .containsExactlyInAnyOrderElementsOf(lines);
        }
    }

    /**
     * what it means is that running your test leaves it perfectly clear whether it passed or failed.
     * JUnit does this and fails with red, which lets you red-green-refactor.
     * By using a testing framework like JUnit, utilizing assertion libraries, and writing specific tests,
     * you can ensure that if a test fails, there will be clear and unambiguous reporting that tells
     * you exactly what passed or failed.
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class SelfValidating {

        @Test
        void addShouldReturnTheSumOfTwoPositiveNumbers() {
            double expected =a+b;
            resultTwo = calculator.add(b, a);
            assertThat(resultTwo).isEqualTo(expected);
        }


        @Test
        void addShouldReturnTheSumOfTwoNegativeNumbers() {
            double expected = -(a+b);
            resultTwo = calculator.add(-b, -a);
            //deliberately failed
            assertThat(resultTwo).isEqualTo(expected);
        }


    }


    static Stream<Arguments> valideMobilePhone() {
        return Stream.of(
                of("+221", "77", ORANGE),
                of("+221", "78", ORANGE),
                of("00221", "77", ORANGE),
                of("00221", "78", ORANGE),
                of("", "77", ORANGE),
                of("", "78", ORANGE),

                of("+221", "76", FREE),
                of("00221", "76", FREE),
                of("", "76", FREE),

                of("+221", "70", EXPRESSO),
                of("00221", "70", EXPRESSO),
                of("", "70", EXPRESSO),

                of("+221", "75", PROMOBILE),
                of("00221", "75", PROMOBILE),
                of("", "75", PROMOBILE)
        );
    }

    static Stream<Arguments> invalideMobilePhone() {
        String number = "9876543";
        return Stream.of(
                of("+222", "70", number),
                of("+221", "71", number),
                of("+221", "70", number.substring(0, 5)),
                of("+221", "70", number.concat("2")),
                of("+221", "70", number.replace("9", "n"))
        );
    }


    /**
     * ==> Timely
     * Practically, You can write unit tests at any time.
     * You can wait up to code is production-ready or you’re better off focusing on writing unit tests in a timely fashion.
     * The idea was that your tests should be written as close to when you write your code as possible (TDD: Before the code).
     * ==>  Thorough (exhaustiv)
     * Should cover every use case scenario and NOT just aim for 100% coverage.
     * Tests for large data sets - this will test runtime and space complexity.
     * Tests for security with users having different roles - behavior may be different based on user's role.
     * Tests for large values - overflow and underflow errors for data types like integer.
     * Tests for exceptions and errors.
     * Tests for illegal arguments or bad inputs.
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
     class ThoroughAndTimely {


        String indicatif, operator;
        String number = "9876543";
        String template = "%s%s%s";
        String phone;



       /* @Test
        void getMobileOperator_withPlusIndicatifAnd77_shouldReturnOrange() {
            indicatif = "+221";
            operator = "77";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_withPlusIndicatifAnd78_shouldReturnOrange() {
            indicatif = "+221";
            operator = "78";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_with00IndicatifAnd77_shouldReturnOrange() {
            indicatif = "00221";
            operator = "77";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_with00IndicatifAnd78_shouldReturnOrange() {
            indicatif = "00221";
            operator = "78";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_withoutIndicatifAnd77_shouldReturnOrange() {
            indicatif = "";
            operator = "77";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_withoutIndicatifAnd78_shouldReturnOrange() {
            indicatif = "";
            operator = "78";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(ORANGE);
        }

        @Test
        void getMobileOperator_withPlusIndicatifAnd76_shouldReturnFree() {
            indicatif = "+221";
            operator = "76";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(FREE);
        }

        @Test
        void getMobileOperator_with00IndicatifAnd76_shouldReturnFree() {
            indicatif = "00221";
            operator = "76";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(FREE);
        }

        @Test
        void getMobileOperator_withoutIndicatifAnd76_shouldReturnFree() {
            indicatif = "";
            operator = "76";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(FREE);
        }

        @Test
        void getMobileOperator_withPlusIndicatifAnd70_shouldReturnExpresso() {
            indicatif = "+221";
            operator = "70";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(EXPRESSO);
        }

        @Test
        void getMobileOperator_with00IndicatifAnd70_shouldReturnExpresso() {
            indicatif = "00221";
            operator = "70";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(EXPRESSO);
        }

        @Test
        void getMobileOperator_withoutIndicatifAnd70_shouldReturnExpresso() {
            indicatif = "";
            operator = "70";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(EXPRESSO);
        }

        @Test
        void getMobileOperator_withPlusIndicatifAnd75_shouldReturnPromobile() {
            indicatif = "+221";
            operator = "75";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(PROMOBILE);
        }

        @Test
        void getMobileOperator_with00IndicatifAnd75_shouldReturnPromobile() {
            indicatif = "00221";
            operator = "75";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(PROMOBILE);
        }

        @Test
        void getMobileOperator_withoutIndicatifAnd75_shouldReturnPromobile() {
            indicatif = "";
            operator = "75";
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getMobileOperator(phone);
            assertThat(result).isEqualTo(PROMOBILE);
        }


        @Test
        void getMobileOperator_withBadIndicatif_shouldThrowError() {
            indicatif = "+222";
            operator = "77";
            //+222779876543
            phone = String.format(template, indicatif, operator, number);
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getMobileOperator(phone)
            );
        }

        @Test
        void getMobileOperator_withBadOperator_shouldThrowError() {
            indicatif = "+221";
            operator = "79";
            //+221799876543
            phone = String.format(template, indicatif, operator, number);
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getMobileOperator(phone)
            );
        }

        @Test
        void getMobileOperator_withNumberLessThan7digits_shouldThrowError() {
            indicatif = "+221";
            operator = "77";
            String currentNumber =number.substring(0, 5);
            //+2217798765
            phone = String.format(template, indicatif, operator, currentNumber);
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getMobileOperator(phone)
            );
        }

        @Test
        void getMobileOperator_withNumberMorThan7digits_shouldThrowError() {
            indicatif = "+221";
            operator = "77";

            String currentNumber =number.concat("2");
            //+2217798765432
            phone = String.format(template, indicatif, operator,currentNumber );
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getMobileOperator(phone)
            );
        }

        @Test
        void getMobileOperator_withBadNumber_shouldThrowError() {
            indicatif = "+221";
            operator = "77";
            phone = String.format(template, indicatif, operator, number.replace("9", "n"));
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getMobileOperator(phone)
            );
        }*/






        @ParameterizedTest
        @MethodSource("sn.ept.git.seminaire.cicd.demo.E_FirstPrincipalTest#valideMobilePhone")
        void getMobileOperator_shouldReturnCorrectOperator(
                String indicatif, String operator, String expected
        ) {
            phone = String.format(template, indicatif, operator, number);
            String result = Validator.getSnMobileOperator(phone);
            assertThat(result).isEqualTo(expected);
        }


        @ParameterizedTest
        @MethodSource("sn.ept.git.seminaire.cicd.demo.E_FirstPrincipalTest#invalideMobilePhone")
        void getMobileOperator_shouldThrowException(
                String indicatif, String operator, String number
        ) {
            phone = String.format(template, indicatif, operator, number);
            assertThrows(
                    BadPhoneException.class,
                    () -> Validator.getSnMobileOperator(phone)
            );
        }


    }
}