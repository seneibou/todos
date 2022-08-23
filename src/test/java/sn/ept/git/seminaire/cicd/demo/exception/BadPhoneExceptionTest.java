package sn.ept.git.seminaire.cicd.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class BadPhoneExceptionTest {

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;
    private BadPhoneException exception;

    @BeforeEach
    void beforeEach() {
        message = "Message";
        cause = new Throwable("empty");
        enableSuppression = false;
        writableStackTrace = false;
    }

    @Test
    void BadPhoneException_WithouthArgs() {
        exception = new BadPhoneException();
        assertEquals(BadPhoneException.class, exception.getClass());
    }

    @Test
    void BadPhoneException_WithMessage() {
        exception = new BadPhoneException(message);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void BadPhoneException_WithCause() {
        exception = new BadPhoneException(cause);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void BadPhoneException_WithMessageAndCause() {
        exception = new BadPhoneException(message, cause);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void BadPhoneException_WithAllArgs() {
        exception = new BadPhoneException(message, cause, enableSuppression, writableStackTrace);
        assertEquals(BadPhoneException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
