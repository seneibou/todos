package sn.ept.git.seminaire.cicd.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class ForbiddenExceptionTest {

    private String message;

    @Test
    void forbiddenException_WithouthArgs_HasDefault() {
        ForbiddenException exception = new ForbiddenException();
        assertEquals("Vous n'êtes pas authorisés à effectuer cette action", exception.getMessage());
    }

    @Test
    void forbiddenException_WithMessage_HasMessage() {
        ForbiddenException exception = new ForbiddenException(message);
        assertEquals(message, exception.getMessage());
    }

}
