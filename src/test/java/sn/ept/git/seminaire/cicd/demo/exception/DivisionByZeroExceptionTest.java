package sn.ept.git.seminaire.cicd.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class DivisionByZeroExceptionTest {

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;
    private DivisionByZeroException exception;

    @BeforeEach
    void beforeEach() {
        message = "Message";
        cause = new Throwable("empty");
        enableSuppression = false;
        writableStackTrace = false;
    }

    @Test
    void DivisionByZeroException_WithouthArgs() {
        exception = new DivisionByZeroException();
        assertEquals(DivisionByZeroException.class, exception.getClass());
    }

    @Test
    void DivisionByZeroException_WithMessage() {
        exception = new DivisionByZeroException(message);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void DivisionByZeroException_WithCause() {
        exception = new DivisionByZeroException(cause);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void DivisionByZeroException_WithMessageAndCause() {
        exception = new DivisionByZeroException(message, cause);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void DivisionByZeroException_WithAllArgs() {
        exception = new DivisionByZeroException(message, cause, enableSuppression, writableStackTrace);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
