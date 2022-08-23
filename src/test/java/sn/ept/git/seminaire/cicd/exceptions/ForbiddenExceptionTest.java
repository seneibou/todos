package sn.ept.git.seminaire.cicd.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class ForbiddenExceptionTest {

    private String message;
    private ForbiddenException exception;

    @Test
    void ForbiddenException_WithouthArgs_HasDefault() {
        exception = new ForbiddenException();
        assertEquals(ForbiddenException.class, exception.getClass());
        assertEquals("Vous n'êtes pas authorisés à effectuer cette action", exception.getMessage());
    }

    @Test
    void itemNotFoundException_WithMessage() {
        message = "empty";
        exception = new ForbiddenException(message);
        assertEquals(ForbiddenException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }
}
