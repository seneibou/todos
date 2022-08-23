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
    }

    @Test
    void DivisionByZeroException_WithouthArgs_ShouldCreateException() {
        exception = new DivisionByZeroException();
        assertEquals(DivisionByZeroException.class, exception.getClass());
    }

    @Test
    void DivisionByZeroException_WithMessage_ShouldCreateException() {
        exception = new DivisionByZeroException(message);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void DivisionByZeroException_WithMessageAndThrowable_ShouldCreateException() {
        exception = new DivisionByZeroException(message, cause);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void DivisionByZeroException_WithThrowable_ShouldCreateException() {
        exception = new DivisionByZeroException(cause);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void DivisionByZeroException_WithAllArgs_ShouldCreateException() {
        enableSuppression = false;
        writableStackTrace = false;
        exception = new DivisionByZeroException(message, cause, enableSuppression,
                writableStackTrace);
        assertEquals(DivisionByZeroException.class, exception.getClass());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
