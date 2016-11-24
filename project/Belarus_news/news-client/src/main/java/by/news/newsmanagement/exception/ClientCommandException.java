package by.epam.newsmanagement.exception;

/**
 * <p>This exception throws when the error has occurred in the business logic.
 * The reason for the error occurred on the client side</p>
 * @author Nikita Kobyzov
 */
public class ClientCommandException extends CommandException {
    public ClientCommandException() {
        super();
    }

    public ClientCommandException(String message) {
        super(message);
    }

    public ClientCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ClientCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ClientCommandException(Throwable cause) {
        super(cause);
    }
}
