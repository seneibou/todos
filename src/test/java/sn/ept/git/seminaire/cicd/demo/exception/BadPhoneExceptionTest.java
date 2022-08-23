package sn.ept.git.seminaire.cicd.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class BadPhoneExceptionTest {

    private String message;
    private Throwable cause;
    boolean enableSuppression;
    boolean writableStackTrace;
    BadPhoneException exception;

    @BeforeEach
    void beforeEach() {
        message = "Test failed";
        cause = new Throwable("empty");
        enableSuppression = false;
        writableStackTrace = false;
    }

    @Test
    void BadPhoneException_WithouthArgs_ShouldCreateException() {
        exception = new BadPhoneException();
        assertEquals(exception.getClass(), BadPhoneException.class);
    }

    @Test
    void BadPhoneException_WithMessage_ShouldCreateException() {
        exception = new BadPhoneException(message);
        assertEquals(exception.getClass(), BadPhoneException.class);
        assertEquals(exception.getMessage(), message);
    }

    @Test
    void BadPhoneException_WithMessageAndThrowable_ShouldCreateException() {
        exception = new BadPhoneException(message, cause);
        assertEquals(exception.getClass(), BadPhoneException.class);
        assertEquals(exception.getMessage(), message);
        assertEquals(exception.getCause(), cause);
    }

    @Test
    void BadPhoneException_WithThrowable_ShouldCreateException() {
        exception = new BadPhoneException(cause);
        assertEquals(exception.getClass(), BadPhoneException.class);
        assertEquals(exception.getCause(), cause);
    }

    @Test
    void BadPhoneException_WithAllArgs_ShouldCreateException() {
        exception = new BadPhoneException(message, cause, enableSuppression,
                writableStackTrace);
        assertEquals(exception.getClass(), BadPhoneException.class);
        assertEquals(exception.getMessage(), message);
        assertEquals(exception.getCause(), cause);
    }
}
