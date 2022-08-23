package sn.ept.git.seminaire.cicd.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class DivisionByZeroExceptionTest {

    private String message;
    private Throwable cause;
    boolean enableSuppression;
    boolean writableStackTrace;
    DivisionByZeroException exception;

    @BeforeEach
    void beforeEach() {
        message = "Test failed";
        cause = new Throwable("empty");
        enableSuppression = false;
        writableStackTrace = false;
    }

    @Test
    void DivisionByZeroException_WithouthArgs_ShouldCreateException() {
        exception = new DivisionByZeroException();
        assertEquals(exception.getClass(), DivisionByZeroException.class);
    }

    @Test
    void DivisionByZeroException_WithMessage_ShouldCreateException() {
        exception = new DivisionByZeroException(message);
        assertEquals(exception.getClass(), DivisionByZeroException.class);
        assertEquals(exception.getMessage(), message);
    }

    @Test
    void DivisionByZeroException_WithMessageAndThrowable_ShouldCreateException() {
        exception = new DivisionByZeroException(message, cause);
        assertEquals(exception.getClass(), DivisionByZeroException.class);
        assertEquals(exception.getMessage(), message);
        assertEquals(exception.getCause(), cause);
    }

    @Test
    void DivisionByZeroException_WithThrowable_ShouldCreateException() {
        exception = new DivisionByZeroException(cause);
        assertEquals(exception.getClass(), DivisionByZeroException.class);
        assertEquals(exception.getCause(), cause);
    }

    @Test
    void DivisionByZeroException_WithAllArgs_ShouldCreateException() {
        exception = new DivisionByZeroException(message, cause, enableSuppression,
                writableStackTrace);
        assertEquals(exception.getClass(), DivisionByZeroException.class);
        assertEquals(exception.getMessage(), message);
        assertEquals(exception.getCause(), cause);
    }
}
