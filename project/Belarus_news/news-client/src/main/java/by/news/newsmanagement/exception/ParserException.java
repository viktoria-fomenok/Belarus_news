package by.epam.newsmanagement.exception;

/**
 * Created by NikitOS on 19.04.2016.
 */
public class ParserException extends ClientCommandException {
    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
