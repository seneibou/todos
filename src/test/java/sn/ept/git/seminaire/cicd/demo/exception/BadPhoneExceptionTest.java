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
    }

    @Test
    void BadPhoneException_WithouthArgs_ShouldCreateException() {
        exception = new BadPhoneException();
        assertEquals(BadPhoneException.class, exception.getClass());
    }

    @Test
    void BadPhoneException_WithMessage_ShouldCreateException() {
        exception = new BadPhoneException(message);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void BadPhoneException_WithMessageAndThrowable_ShouldCreateException() {
        exception = new BadPhoneException(message, cause);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void BadPhoneException_WithThrowable_ShouldCreateException() {
        exception = new BadPhoneException(cause);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void BadPhoneException_WithAllArgs_ShouldCreateException() {
        enableSuppression = false;
        writableStackTrace = false;
        exception = new BadPhoneException(message, cause, enableSuppression,
                writableStackTrace);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
