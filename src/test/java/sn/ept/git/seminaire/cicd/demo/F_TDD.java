package sn.ept.git.seminaire.cicd.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

@Slf4j
class F_TDD {

    static Stream<Arguments> testData() {
        return Stream.of(
                of( Arrays.asList(1,2,0,3), 4),
                of(Arrays.asList(1,2,7,3), 4),
                of(Arrays.asList(1,2,9,3),4),
                of(Arrays.asList(1,2,11,3),4),
                of(Arrays.asList(1,2), 4),
                of(Arrays.asList(2),4),
                of(Arrays.asList(),0),
                of(Arrays.asList(-1),0),
                of(Arrays.asList(1,2,4,3),20),
                of(Arrays.asList(1,2,4,6),56),
                of(Arrays.asList(0,2,4,8,1,3),84),
                of(Arrays.asList(-1,-2,-4,-3),20),
                of(Arrays.asList(-1,-2,-4,-6),56),
                of(Arrays.asList(-0,-2,-4,-8,-1,-3),84)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldReturnSumOfSquaresOfEvenNumbers(List<Integer> values, long result) {
        assertThat(Validator.sumOfSquaresOfEvenNumbers(values)).isEqualTo(result);
    }

}