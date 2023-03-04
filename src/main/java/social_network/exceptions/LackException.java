package social_network.exceptions;

/**
 * Exception class for entities that doesn't exist
 */
public class LackException extends RuntimeException{
    public LackException() {
    }

    public LackException(String message) {
        super(message);
    }

    public LackException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackException(Throwable cause) {
        super(cause);
    }

    protected LackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
