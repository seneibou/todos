package sn.ept.git.seminaire.cicd.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

@Slf4j
class F_TDD {

    static Stream<Arguments> phonesStates() {
        return Stream.of(
                of("00221779876543",true),
                of("+221779876543",true),
                of("779876543",true),

                of("00221789876543",true),
                of("+221789876543",true),
                of("789876543",true),

                of("00221769876543",true),
                of("+221769876543",true),
                of("769876543",true),

                of("00221759876543",true),
                of("+221759876543",true),
                of("759876543",true),

                of("00221709876543",true),
                of("+221709876543",true),
                of("709876543",true),

                //mauvais indicatif
                of("00222709876543",false),
                of("+222709876543",false),

                //mauvais code operateur
                of("719876543",false),
                of("799876543",false),

                //chiffres manquants
                of("0022170987654",false),
                of("+22170987654",false),
                of("70987654",false),

                //chiffres de trop
                of("002217098765432",false),
                of("+2217098765432",false),
                of("7098765432",false),

                //cas extremes
                of("",false),
                of(" ",false),
                of("abc",false)
        );
    }

    @ParameterizedTest
    @MethodSource("phonesStates")
    void shouldValidatePhonesNumbers(String phone, boolean isValid) {
        assertThat(Validator.validateSnMobilePhone(phone)).isEqualTo(isValid);
    }

}