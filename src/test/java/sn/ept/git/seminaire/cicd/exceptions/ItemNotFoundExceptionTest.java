package sn.ept.git.seminaire.cicd.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class ItemNotFoundExceptionTest {

    private String message;
    private Throwable cause;
    private ItemNotFoundException exception;

    @BeforeEach
    void beforeEach() {
        message = "empty";
        cause = new Throwable("empty");
    }

    @Test
    void itemNotFoundException_WithouthArgs_HasDefault() {
        exception = new ItemNotFoundException();
        assertEquals(ItemNotFoundException.class, exception.getClass());
        assertEquals("Impossible de retrouver l'élément recherché", exception.getMessage());
    }

    @Test
    void itemNotFoundException_WithMessageAndCause() {
        exception = new ItemNotFoundException(message, cause);
        assertEquals(ItemNotFoundException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void itemNotFoundException_WithCause() {
        exception = new ItemNotFoundException(cause);
        assertEquals(ItemNotFoundException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }
}
