package social_network.exceptions;

public class FriendshipException extends RuntimeException {
    public FriendshipException() {

    }

    public FriendshipException(String message) {
        super(message);
    }

    public FriendshipException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendshipException(Throwable cause) {
        super(cause);
    }

    protected FriendshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
