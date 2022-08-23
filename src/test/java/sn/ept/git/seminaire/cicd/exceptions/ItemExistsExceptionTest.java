package sn.ept.git.seminaire.cicd.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class ItemExistsExceptionTest {

    private String message;
    private Throwable cause;
    private ItemExistsException exception;

    @BeforeEach
    void beforeEach() {
        message = "empty";
        cause = new Throwable("empty");
    }

    @Test
    void itemExistsException_WithouthArgs_HasDefault() {
        exception = new ItemExistsException();
        assertEquals(ItemExistsException.class, exception.getClass());
        assertEquals("Un des éléments que vous tentez d'jouter existe déjà ", exception.getMessage());
    }

    @Test
    void itemExistsException_WithMessageAndCause() {
        exception = new ItemExistsException(message, cause);
        assertEquals(ItemExistsException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void itemExistsException_WithCause() {
        exception = new ItemExistsException(cause);
        assertEquals(ItemExistsException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }
}
