package by.epam.newsmanagement.exception;


/**
 * <p>This exception throws when the error has occurred in the business logic.
 * The reason for the error occurred on the server side</p>
 * @author Nikita Kobyzov
 */
public class ServerCommandException extends CommandException {
    public ServerCommandException() {
        super();
    }

    public ServerCommandException(String message) {
        super(message);
    }

    public ServerCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerCommandException(Throwable cause) {
        super(cause);
    }

    protected ServerCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
