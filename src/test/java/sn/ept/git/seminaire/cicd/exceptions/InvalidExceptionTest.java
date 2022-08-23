package sn.ept.git.seminaire.cicd.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class InvalidExceptionTest {

    private String message;
    private InvalidException exception;

    @Test
    void invalidException_WithMessage() {
        message = "empty";
        exception = new InvalidException(message);
        assertEquals(InvalidException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }

}
